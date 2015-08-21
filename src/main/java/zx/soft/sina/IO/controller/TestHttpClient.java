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

import zx.soft.sina.IO.service.RedisService;

@Controller
@RequestMapping("/http")
public class TestHttpClient {

	@Inject
	private RedisService redisService;

	@RequestMapping(value = "/nums", method = RequestMethod.POST, headers = "content-type=application/json")
	@ResponseStatus(HttpStatus.OK)
	public void insertUsers(@RequestBody List<String> nums) throws IOException {
		try {
			redisService.insertnum(nums);
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
