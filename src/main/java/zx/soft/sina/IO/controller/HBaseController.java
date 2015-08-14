package zx.soft.sina.IO.controller;

import java.io.IOException;
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

import zx.soft.sina.IO.domain.User;
import zx.soft.sina.IO.domain.Weibo;
import zx.soft.sina.IO.service.HBaseService;

@Controller
@RequestMapping("/hbase")
public class HBaseController {

	@Inject
	private HBaseService hBaseService;

	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUsers(@RequestBody List<User> users) throws IOException {
		try {
			hBaseService.insert(users);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/weibos", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertWeibos(@RequestBody List<Weibo> weibos) throws IOException {
		try {
			hBaseService.insert(weibos);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
