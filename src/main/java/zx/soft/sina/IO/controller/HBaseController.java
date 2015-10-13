package zx.soft.sina.IO.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/users/info", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUsers(@RequestBody List<User> users) {
		if (users.size() > 0) {
			try {
				hBaseService.insertUsers(users);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/users/score", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertUserScores(@RequestBody Map<String, String> ids_scores) {
		if (ids_scores.size() > 0) {
			try {
				hBaseService.inserUserScores(ids_scores);
			} catch (SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/weibos/history", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertWeibos(@RequestBody List<Weibo> weibos) {
		if (weibos.size() > 0) {
			try {
				hBaseService.insertHistoryWeibos(weibos);
			} catch (SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/weibos/lastest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void insertLastestWeibos(@RequestBody List<Weibo> weibos) {
		if (weibos.size() > 0) {
			try {
				hBaseService.insertlastestWeibos(weibos);
			} catch (SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/weibos", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getWeiboCount(@RequestParam("timeStamp") long timeStamp, HttpServletResponse response)
			throws SQLException {

		if (timeStamp < 1441929600000L) {
			return "timeStamp should greater than 1441929600000L.";
		} else if (timeStamp > (System.currentTimeMillis() + 3600_000)) {
			return "timeStamp should not more than the current time for one hour.";
		} else {
			return hBaseService.getHistoryWeiboCount(timeStamp);
		}
	}

}
