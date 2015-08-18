package zx.soft.sina.IO.controller;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.query.ImpalaQuery;

@Controller
@RequestMapping("/weibos")
public class WeiboController {

	/**
	 * 获取循环爬取的活跃用户微博中最大的微博id号，供循环爬取时传递since_id,用于去重
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/active/maxid", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getMaxId() throws SQLException {
		String maxId = ImpalaQuery.getMaxId();
		return maxId;
	}
}
