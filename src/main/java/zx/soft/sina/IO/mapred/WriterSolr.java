package zx.soft.sina.IO.mapred;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.utils.config.ConfigUtil;

public class WriterSolr implements SinaWriter {

	private static Logger logger = LoggerFactory.getLogger(WriterSolr.class);
	private final CloudSolrServer cloudSolrServer;

	public WriterSolr() throws MalformedURLException {

		Properties props = ConfigUtil.getProps("solr.properties");
		cloudSolrServer = new CloudSolrServer(props.getProperty("zookeeper_cloud") + "/solr");
		cloudSolrServer.setDefaultCollection(props.getProperty("collection"));
		cloudSolrServer.setZkConnectTimeout(Integer.parseInt(props.getProperty("zookeeper_connect_timeout")));
		cloudSolrServer.setZkClientTimeout(Integer.parseInt(props.getProperty("zookeeper_client_timeout")));
		cloudSolrServer.connect();
	}

	@Override
	public void write(String key, SimpleUser user) {
		try {
			cloudSolrServer.add(transUser(user));
			logger.info("index " + user.getId() + " to solr");
			cloudSolrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		cloudSolrServer.shutdown();
	}

	private SolrInputDocument transUser(SimpleUser user) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", user.getId());
		doc.addField("created_at", user.getCreated_at());
		doc.addField("idstr", user.getIdstr());
		//		doc.addField("screen_name", user.getScreen_name());
		if (user.getName() != "") {
			doc.addField("name", user.getName());
		}
		//		if (user.getUclass() != 0) {
		//			doc.addField("uclass", user.getUclass());
		//		}
		//		if (user.getProvince() != 0) {
		//			doc.addField("province", user.getProvince());
		//		}
		//		if (user.getCity() != 0) {
		//			doc.addField("city", user.getCity());
		//		}
		//		if (user.getLocation() != "") {
		//			doc.addField("location", user.getLocation());
		//		}
		//		if (user.getDescription() != "") {
		//			doc.addField("description", user.getDescription());
		//		}
		//		if (user.getUrl() != "") {
		//			doc.addField("url", user.getUrl());
		//		}
		//		if (user.getProfile_image_url() != "") {
		//			doc.addField("profile_image_url", user.getProfile_image_url());
		//		}
		//		if (user.getProfile_url() != "") {
		//			doc.addField("profile_url", user.getProfile_url());
		//		}
		//		if (user.getDomain() != "") {
		//			doc.addField("domain", user.getDomain());
		//		}
		//		if (user.getWeihao() != "") {
		//			doc.addField("weihao", user.getWeihao());
		//		}
		//		if (user.getGender() != "") {
		//			doc.addField("gender", user.getGender());
		//		}
		//		if (user.getFollowers_count() != 0) {
		//			doc.addField("followers_count", user.getFollowers_count());
		//		}
		//		if (user.getFriends_count() != 0) {
		//			doc.addField("friends_count", user.getFriends_count());
		//		}
		//		if (user.getPagefriends_count() != 0) {
		//			doc.addField("pagefriends_count", user.getPagefriends_count());
		//		}
		//		if (user.getStatuses_count() != 0) {
		//			doc.addField("statuses_count", user.getStatuses_count());
		//		}
		//		if (user.getFavourites_count() != 0) {
		//			doc.addField("favourites_count", user.getFavourites_count());
		//		}
		//		if (user.getVerified_type() != 0) {
		//			doc.addField("verified_type", user.getVerified_type());
		//		}
		//		if (user.getRemark() != "") {
		//			doc.addField("remark", user.getRemark());
		//		}
		//		if (user.getPtype() != 0) {
		//			doc.addField("ptype", user.getPtype());
		//		}
		//		if (user.getAvatar_large() != "") {
		//			doc.addField("avatar_large", user.getAvatar_large());
		//		}
		//		if (user.getAvatar_hd() != "") {
		//			doc.addField("avatar_hd", user.getAvatar_hd());
		//		}
		//		if (user.getVerified_reason() != "") {
		//			doc.addField("verified_reason", user.getVerified_reason());
		//		}
		//		if (user.getVerified_type() != 0) {
		//			doc.addField("verified_trade", user.getVerified_type());
		//		}
		//		if (user.getVerified_reason_url() != "") {
		//			doc.addField("verified_reason_url", user.getVerified_reason_url());
		//		}
		//		if (user.getVerified_source() != "") {
		//			doc.addField("verified_source", user.getVerified_source());
		//		}
		//		if (user.getVerified_source_url() != "") {
		//			doc.addField("verified_source_url", user.getVerified_source_url());
		//		}
		//		if (user.getVerified_state() != 0) {
		//			doc.addField("verified_state", user.getVerified_state());
		//		}
		//		if (user.getVerified_level() != 0) {
		//			doc.addField("verified_level", user.getVerified_level());
		//		}
		//		if (user.getVerified_reason_modified() != "") {
		//			doc.addField("verified_reason_modified", user.getVerified_reason_modified());
		//		}
		//		if (user.getVerified_contact_name() != "") {
		//			doc.addField("verified_contact_name", user.getVerified_contact_name());
		//		}
		//		if (user.getVerified_contact_email() != "") {
		//			doc.addField("verified_contact_email", user.getVerified_contact_email());
		//		}
		//		if (user.getVerified_contact_mobile() != "") {
		//			doc.addField("verified_contact_mobile", user.getVerified_contact_mobile());
		//		}
		//		if (user.getOnline_status() != 0) {
		//			doc.addField("online_status", user.getOnline_status());
		//		}
		//		if (user.getBi_followers_count() != 0) {
		//			doc.addField("bi_followers_count", user.getBi_followers_count());
		//		}
		//		if (user.getLang() != "") {
		//			doc.addField("lang", user.getLang());
		//		}
		//		if (user.getStar() != 0) {
		//			doc.addField("star", user.getStar());
		//		}
		//		if (user.getMbtype() != 0) {
		//			doc.addField("mbtype", user.getMbtype());
		//		}
		//		if (user.getMbrank() != 0) {
		//			doc.addField("mbrank", user.getMbrank());
		//		}
		//		if (user.getBlock_word() != 0) {
		//			doc.addField("block_word", user.getBlock_word());
		//		}
		//		if (user.getBlock_app() != 0) {
		//			doc.addField("block_app", user.getBlock_app());
		//		}
		//		if (user.getCredit_score() != 0) {
		//			doc.addField("credit_score", user.getCredit_score());
		//		}
		//		if (user.getUser_ability() != 0) {
		//			doc.addField("user_ability", user.getUser_ability());
		//		}
		//		if (user.getUrank() != 0) {
		//			doc.addField("urank", user.getUrank());
		//		}
		return doc;
	}
}
