package com.example.demo.controller.api;

import com.example.demo.dto.request.BoardFormRequest;
import com.example.demo.dto.response.Response;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public Response<Integer> createBoard(@RequestBody BoardFormRequest boardFormRequest) {
        boardService.createBoard(boardFormRequest);
        return Response.success(boardFormRequest.getBoardId());
    }

    @PutMapping("/{boardId}")
    public Response<Integer> updateBoard(@PathVariable Integer boardId, @RequestBody BoardFormRequest boardFormRequest) {
        boardService.updateBoard(boardId, boardFormRequest);
        return Response.success(boardId);
    }

    @DeleteMapping("/{boardId}")
    public Response<Void> deleteBord(@PathVariable Integer boardId, @RequestBody BoardFormRequest boardFormRequest) {
        boardService.deleteBoard(boardId, boardFormRequest.getBoardPassword());
        return Response.success();
    }
}
