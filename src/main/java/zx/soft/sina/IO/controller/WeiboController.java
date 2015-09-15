package zx.soft.sina.IO.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.query.ImpalaQuery;
import zx.soft.sina.IO.relationship.Relation;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.weibo.mapred.domain.Comments;
import zx.soft.weibo.mapred.domain.Weibo;
import zx.soft.weibo.sina.api.SinaWeiboAPI;

@Controller
@RequestMapping("/weibos")
public class WeiboController {

	private static String source;
	static {
		Properties props = ConfigUtil.getProps("super.properties");
		source = props.getProperty("super");
	}

	/**
	 * 获取循环爬取的活跃用户微博中最大的微博id号，供循环爬取时传递since_id,用于去重
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/active/maxid", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getMaxId() throws SQLException {
		String maxId = ImpalaQuery.getMaxId();
		return maxId;
	}

	@RequestMapping(value = "/lastest", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Weibo> getWeibos(@RequestParam("screen_name") String screenName) throws SQLException {
		SinaWeiboAPI api = new SinaWeiboAPI(new HttpClientDaoImpl());
		List<Weibo> weibos = Relation.getWeibos(screenName, source, api);
		return weibos;
	}

	@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Comments> getComments(@PathVariable("id") String id) throws SQLException {
		SinaWeiboAPI api = new SinaWeiboAPI(new HttpClientDaoImpl());
		List<Comments> comments = Relation.getComments(id, api, source);
		return comments;
	}
}
