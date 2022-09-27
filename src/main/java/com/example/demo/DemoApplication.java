package com.example.demo;

import com.example.demo.dao.TodoDAO;
import com.example.demo.model.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private final TodoDAO todoDAO;

	@RequestMapping("/news")
	public String news(Model model) throws Exception {
		Map<String, Object> rtnObj = new HashMap<> ();

		List<Todo> list = todoDAO.list();
		logger.info("news->" + list.toString());

		rtnObj.put("list", list);
		model.addAttribute("list", rtnObj);
		return "index";
	}
}
