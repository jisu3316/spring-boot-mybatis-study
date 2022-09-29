package com.example.demo;

import com.example.demo.dao.TodoDAO;
import com.example.demo.model.Todo;
import com.example.demo.response.Response;
import com.example.demo.response.TodoResponse;
import com.example.demo.service.PaginationService;
import com.example.demo.service.TodoService;
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
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private final TodoDAO todoDAO;
	private final TodoService todoService;

	private final PaginationService paginationService;

	@RequestMapping("/news")
	public String news(Model model) throws Exception {
		Map<String, Object> rtnObj = new HashMap<> ();

//		List<Todo> list = todoDAO.list();
		List<TodoResponse> list = todoService.getTodo();
		logger.info("news->" + list.toString());

		rtnObj.put("list", list);
		model.addAttribute("list", rtnObj);
		List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(0, 1);
		model.addAttribute("paginationBarNumbers", paginationBarNumbers);
		return "index";
	}

	@PutMapping("/news/{todoId}")
	@ResponseBody
	public Response<Todo> newss(@PathVariable Long todoId, Model model) {
		Todo modify = todoService.modify(todoId);
		return Response.success(modify);
	}

	@GetMapping("/test")
	public String newss() {
		return "home";
	}
}
