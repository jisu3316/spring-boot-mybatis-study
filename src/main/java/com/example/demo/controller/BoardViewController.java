package com.example.demo.controller;


import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/view/board")
public class BoardViewController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final PaginationService paginationService;

    @GetMapping
    public String boards(Model model, SearchBoardRequest boardRequest) {
        log.info("searchType : {}", boardRequest.getSearchType());
        log.info("searchValue : {}", boardRequest.getSearchValue());
        log.info("startDate : {}", boardRequest.getStartDate());
        log.info("endDate : {}", boardRequest.getEndDate());

        Integer searchTotalCount = boardService.getSearchTotalCount(boardRequest);

        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(boardRequest.getPage(), searchTotalCount);
        model.addAttribute("boards", boardService.boards(boardRequest).stream().map(BoardResponse::from).collect(Collectors.toList()));
        model.addAttribute("search", boardRequest);
        model.addAttribute("page", boardRequest.getPage());
        model.addAttribute("paginationBarNumbers", paginationBarNumbers);
        model.addAttribute("total", paginationService.getEndNumber(boardRequest.getPage(), searchTotalCount));
        return "board/list";
    }

    @GetMapping("/new")
    public String createBoard(Model model, Integer page) {
        model.addAttribute("page", page);
        return "board/new-board";
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable Integer boardId, Model model, Integer page) {
        model.addAttribute("comments", commentService.getComments(boardId));
        model.addAttribute("board", BoardResponse.from(boardService.getBoard(boardId)));
        model.addAttribute("page", page);
        return "board/detail";
    }

    @GetMapping("/{boardId}/form")
    public String updateBoard(@PathVariable Integer boardId, Model model, Integer page) {
        model.addAttribute("board", boardService.getBoard(boardId));
        model.addAttribute("page", page);
        return "board/form";
    }

}
