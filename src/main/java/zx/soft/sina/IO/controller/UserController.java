package zx.soft.sina.IO.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.sina.IO.query.ImpalaQuery;

@Controller
@RequestMapping("/users")
public class UserController {

	/**
	 * 利用impala查询用户活跃得分排名居于num之后的一千个用户id号
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/active/{num}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<String> getTopNActiveUser(@PathVariable int num) throws SQLException {
		List<String> topN = ImpalaQuery.getTopNActiveUser(num);
		return topN;
	}

}
