package zx.soft.sina.IO.controller;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.query.ImpalaQuery;
import zx.soft.sina.IO.service.UserToCDH5Service;
import zx.soft.sina.IO.service.UserToHBaseService;
import zx.soft.sina.IO.service.UserToMySQLService;
import zx.soft.sina.IO.service.UserToRedisService;
import zx.soft.sina.IO.service.UserToSolrService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Inject
	private UserToHBaseService userToHBaseService;
	@Inject
	private UserToRedisService userToRedisService;
	@Inject
	private UserToSolrService userToSolrService;
	@Inject
	private UserToMySQLService userToMySQLService;
	@Inject
	private UserToCDH5Service userToCDH5Service;

	@RequestMapping(value = "/redis", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserToRedis(@RequestBody SimpleUser user) {
		userToRedisService.insertUser(user);
		userToRedisService.close();
	}

	@RequestMapping(value = "/solr", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserToSolr(@RequestBody SimpleUser user) {
		userToSolrService.insertUser(user);
		userToSolrService.close();
	}

	@RequestMapping(value = "/mysql", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserToMySQL(@RequestBody SimpleUser user) {
		userToMySQLService.insertUser(user);
	}

	@RequestMapping(value = "/CDH5", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserToCDH5(@RequestBody SimpleUser user) {
		userToCDH5Service.insertUser(user);
		userToCDH5Service.close();
	}

	@RequestMapping(value = "/redis/{user}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void getUser(@PathVariable String user) {
		userToRedisService.getUser(user);
		userToRedisService.close();
	}

	@RequestMapping(value = "/HBase", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserToHBase(@RequestBody SimpleUser user) {
		userToHBaseService.insertUser(user);
		userToHBaseService.close();
	}

	@RequestMapping(value = "/active/{num}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<String> getTopNActiveUser(@PathVariable int num) throws SQLException {
		List<String> topN = ImpalaQuery.getTopNActiveUser(num);
		return topN;
	}

}
