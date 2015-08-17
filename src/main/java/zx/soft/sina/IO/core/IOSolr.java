package zx.soft.sina.IO.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.utils.config.ConfigUtil;

public class IOSolr implements SinaIO {

	private static Logger logger = LoggerFactory.getLogger(IOSolr.class);
	private final CloudSolrServer cloudSolrServerUser;
	private final CloudSolrServer cloudSolrServerWeibo;

	public IOSolr() throws MalformedURLException {

		Properties props = ConfigUtil.getProps("solr.properties");
		cloudSolrServerUser = new CloudSolrServer(props.getProperty("zookeeper_cloud") + "/solr");
		cloudSolrServerUser.setDefaultCollection(props.getProperty("user_collection"));
		cloudSolrServerUser.setZkConnectTimeout(Integer.parseInt(props.getProperty("zookeeper_connect_timeout")));
		cloudSolrServerUser.setZkClientTimeout(Integer.parseInt(props.getProperty("zookeeper_client_timeout")));
		cloudSolrServerWeibo = new CloudSolrServer(props.getProperty("zookeeper_cloud") + "/solr");
		cloudSolrServerWeibo.setDefaultCollection(props.getProperty("weibo_collection"));
		cloudSolrServerWeibo.setZkConnectTimeout(Integer.parseInt(props.getProperty("zookeeper_connect_timeout")));
		cloudSolrServerWeibo.setZkClientTimeout(Integer.parseInt(props.getProperty("zookeeper_client_timeout")));

		cloudSolrServerWeibo.connect();
		cloudSolrServerUser.connect();
	}

	@Override
	public <T> void write(String key, T value) {
		try {
			if (value instanceof User) {
				cloudSolrServerUser.add(trans(value));
				cloudSolrServerUser.commit();
			}
			if (value instanceof Weibo) {
				cloudSolrServerWeibo.add(transWeibo(value));
				cloudSolrServerWeibo.commit();
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		cloudSolrServerUser.shutdown();
		cloudSolrServerWeibo.shutdown();
	}

	private <T> SolrInputDocument trans(T value) {
		SolrInputDocument doc = new SolrInputDocument();
		Field[] fields = value.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object obj = field.get(value);
				doc.addField(field.getName(), obj);
				logger.info("add field　" + field.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	private <T> SolrInputDocument transWeibo(T value) {
		SolrInputDocument doc = new SolrInputDocument();
		Field[] fields = value.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				if (field.getName().equals("user") || field.getName().equals("pic_urls")
						|| field.getName().equals("visible") || field.getName().equals("darwin_tags")) {
					if (field.get(value) != null) {
						doc.addField(field.getName(), field.get(value).toString());
						logger.info("add field " + field.getName());
					}

				} else {
					doc.addField(field.getName(), field.get(value));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

}
