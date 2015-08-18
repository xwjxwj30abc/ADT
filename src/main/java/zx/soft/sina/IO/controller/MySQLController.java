package zx.soft.sina.IO.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.domain.SimpleUser;
import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.service.MySQLService;

@Controller
@RequestMapping("/mysql")
public class MySQLController {
	@Inject
	private MySQLService mySQLService;

	@RequestMapping(value = "/simpleusers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertSimpleUserToMySQL(@RequestBody List<SimpleUser> values) {
		mySQLService.insert(values);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserToMySQL(@RequestBody List<User> values) {
		mySQLService.insert(values);
	}

	@RequestMapping(value = "/weibos", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertWeiboToMySQL(@RequestBody List<Weibo> values) {
		mySQLService.insert(values);
	}
}
