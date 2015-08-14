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

import zx.soft.sina.IO.mapred.WriterSolr;
import zx.soft.utils.config.ConfigUtil;

public class IOSolr implements SinaIO {

	private static Logger logger = LoggerFactory.getLogger(WriterSolr.class);
	private final CloudSolrServer cloudSolrServer;

	public IOSolr() throws MalformedURLException {

		Properties props = ConfigUtil.getProps("solr.properties");
		cloudSolrServer = new CloudSolrServer(props.getProperty("zookeeper_cloud") + "/solr");
		cloudSolrServer.setDefaultCollection(props.getProperty("collection"));
		cloudSolrServer.setZkConnectTimeout(Integer.parseInt(props.getProperty("zookeeper_connect_timeout")));
		cloudSolrServer.setZkClientTimeout(Integer.parseInt(props.getProperty("zookeeper_client_timeout")));
		cloudSolrServer.connect();
	}

	@Override
	public <T> void write(String key, T value) {
		try {
			cloudSolrServer.add(trans(value));
			cloudSolrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		cloudSolrServer.shutdown();
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

}
