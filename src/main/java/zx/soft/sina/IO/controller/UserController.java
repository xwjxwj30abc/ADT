package zx.soft.sina.IO.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

	/*
	@Inject
	private UserToSolrService userToSolrService;
	@Inject
	private UserToMySQLService userToMySQLService;
	@Inject
	private UserToCDH5Service userToCDH5Service;

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


	@RequestMapping(value = "/active/{num}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<String> getTopNActiveUser(@PathVariable int num) throws SQLException {
		List<String> topN = ImpalaQuery.getTopNActiveUser(num);
		return topN;
	}*/

}
