package zx.soft.sina.IO.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.service.RedisService;

@Controller
@RequestMapping("/redis")
public class RedisController {

	@Inject
	private RedisService redisService;

	@RequestMapping(value = "/users", method = RequestMethod.POST, headers = "content-type=application/json")
	@ResponseStatus(HttpStatus.OK)
	public void insertUsers(@RequestBody List<User> users) throws IOException {
		try {
			redisService.insert(users);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/weibos", method = RequestMethod.POST, headers = "content-type=application/json")
	@ResponseStatus(HttpStatus.OK)
	public void insertWeibos(@RequestBody List<Weibo> weibos) throws IOException {
		try {
			redisService.insert(weibos);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
