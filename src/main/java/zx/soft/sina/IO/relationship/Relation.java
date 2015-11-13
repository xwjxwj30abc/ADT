package zx.soft.sina.IO.relationship;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.weibo.mapred.domain.Comments;
import zx.soft.weibo.mapred.domain.Weibo;
import zx.soft.weibo.mapred.utils.SinaDomainUtils;
import zx.soft.weibo.sina.api.SinaWeiboAPI;
import zx.soft.weibo.sina.domain.SinaDomain;

public class Relation {

	private static Logger logger = LoggerFactory.getLogger(Relation.class);

	private static final String COOKIE = "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFkT5yBjUZExKNJDuy2PW445JpX5KMt; UOR=www.chinaz.com,widget.weibo.com,login.sina.com.cn; SINAGLOBAL=4774358005242.229.1404870921222; ULV=1413773333874:51:10:1:2119691141365.4128.1413773333866:1413456152178; ALF=1445309399; wvr=5; SUS=SID-1854067572-1413773402-GZ-yfh2c-a128b79202962112a83158db7a51d952; SUE=es%3D72104da67ccfc14c5560bfc55f6bcff0%26ev%3Dv1%26es2%3D4bdb1977f777eb9b1c59cc2252adbb2b%26rs0%3DL8aHGdXR8ScyLLdLmYeM44%252F6kzvSJGdZGl%252FymLVU%252BO50A2r8tnvfvrT%252Ft7D0n00ibLTzE%252FOHkzftB%252BmEk57HvsMfXed%252F%252FXhko3%252BqjOLvk3jQC7yjbff4Nn5m%252FO2ygZN4Qn5gyVFxVDDLk2QXSxlHqlT1VryqxTEAvfwvcIdw8hM%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1413773402%26et%3D1413859802%26d%3Dc909%26i%3Dd952%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D1854067572%26name%3D18256911213%2540sina.cn%26nick%3D182%252A%252A%252A%252A%252A213%2540sina.cn%26fmp%3D%26lcp%3D; SUB=_2AkMjGPdta8NlrAJZnfwVymzna49H-jyQzv2bAn7uJhIyGxgv7kdVqSUJJjOdUZXvnZWk3VI1y76p_h9iQA..; SSOLoginState=1413773402; _s_tentry=login.sina.com.cn; Apache=2119691141365.4128.1413773333866; JSESSIONID=494DA94A475EAB3D720565F4824030A3";
	private static final int PAGE_COUNT = 50;

	public static List<Weibo> getWeibos(String screenName, String source, SinaWeiboAPI api) {
		List<Weibo> weibos = new ArrayList<>();
		SinaDomain sinaDomain = api.statusesUserTimelineByScreenName(screenName, "0", "0", 100, 1, 0, 0, 0, source);
		if (sinaDomain.containsKey("error_code") && sinaDomain.getFieldValue("error_code").toString().equals("10022")) {
			logger.error("IP requests out of rate limit，return null");
		} else if (sinaDomain != null) {
			weibos = SinaDomainUtils.getUserWeibos(sinaDomain);
		}
		return weibos;
	}

	/*
	 * 根据微博ID返回某条微博的评论列表，可能需要传入cookie参数。
	 * id:需要查询的微博ID。
	 * since_id:若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
	 * max_id:若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
	 * count:单页返回的记录条数，默认为50。
	 * page:返回结果的页码，默认为1。
	 * filter_by_author:作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
	 */
	public static List<Comments> getComments(String id, SinaWeiboAPI api, String source) {
		int page = 1;
		boolean flag = true;
		List<Comments> comments = new ArrayList<>();
		while (flag) {
			List<Comments> comms = null;
			SinaDomain sinaDomain = api.commentsShow(COOKIE, id, "0", "0", PAGE_COUNT, page++, 0, source);
			if (sinaDomain.containsKey("error_code")
					&& sinaDomain.getFieldValue("error_code").toString().equals("10022")) {
				logger.error("IP requests out of rate limit，return null,please update source");
				flag = false;
			}
			if (sinaDomain != null) {
				comms = SinaDomainUtils.getWeiboComments(sinaDomain);
				if (comms.size() > 0) {
					comments.addAll(comms);
				} else {
					flag = false;
				}
			}
		}
		return comments;
	}

	public static List<Comments> getReposts(String id, SinaWeiboAPI api, String source) {
		int page = 1;
		boolean flag = true;
		List<Comments> comments = new ArrayList<>();
		while (flag) {
			List<SinaDomain> sinaDomains = api.repostsShow(COOKIE, id, "0", "0", PAGE_COUNT, page++, source);
			if (sinaDomains != null && sinaDomains.size() > 0) {
				for (SinaDomain sinaDomain : sinaDomains) {
					comments.add(SinaDomainUtils.getWeiboReposts(sinaDomain));
				}
			} else {
				flag = false;
			}
		}
		return comments;
	}

	public static void main(String[] args) {
		SinaWeiboAPI api = new SinaWeiboAPI(new HttpClientDaoImpl());
		List<Weibo> weibos = Relation.getWeibos("暂时胆小", "3845272542", api);
		//System.out.println(weibos.size());
		List<Comments> comments = Relation.getComments("3905364770682830", api, "3442868347");//3194139109
		List<Comments> reposts = Relation.getReposts("3905364770682830", api, "3442868347");//3194139109 861 461
		System.out.println(comments.size());
		System.out.println(reposts.size());
	}
}
