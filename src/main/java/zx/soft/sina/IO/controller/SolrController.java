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
import zx.soft.sina.IO.service.SolrService;

@Controller
@RequestMapping("/solr")
public class SolrController {

	@Inject
	private SolrService solrService;

	@RequestMapping(value = "/users", method = RequestMethod.POST, headers = "content-type=application/json")
	@ResponseStatus(HttpStatus.OK)
	public void insertUsers(@RequestBody List<User> users) throws IOException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		solrService.insert(users);
		solrService.close();
	}

	@RequestMapping(value = "/weibos", method = RequestMethod.POST, headers = "content-type=application/json")
	@ResponseStatus(HttpStatus.OK)
	public void insertWeibos(@RequestBody List<Weibo> weibos) throws IOException, NoSuchFieldException,
	SecurityException, IllegalArgumentException, IllegalAccessException {
		solrService.insert(weibos);
		solrService.close();
	}

}
