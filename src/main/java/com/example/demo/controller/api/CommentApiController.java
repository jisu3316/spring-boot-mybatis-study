package com.example.demo.controller.api;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.dto.response.Response;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public Response<List<CommentResponse>> getComments(@PathVariable Integer boardId) {
        List<CommentResponse> comments = commentService.getComments(boardId);
        return Response.success(comments);
    }

    @PostMapping
    public Response<Integer> createComment(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
        commentService.updateGroupId(commentRequest.getCommentId());
        return Response.success(commentRequest.getBoardId());
    }

    @PutMapping("/{commentId}")
    public Response<Integer> updateComment(@PathVariable Integer commentId, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentId, commentRequest);
        return Response.success(commentRequest.getBoardId());
    }

    @DeleteMapping("/{commentId}")
    public Response<Integer> deleteComment(@PathVariable Integer commentId, @RequestBody CommentRequest commentRequest) {
        commentService.deleteComment(commentId, commentRequest);
        return Response.success(commentId);
    }

    @PostMapping("/{commentId}")
    public Response<Integer> reComment(@PathVariable Integer commentId, @RequestBody CommentRequest commentRequest) {
        commentService.reComment(commentId, commentRequest);
        return Response.success(commentRequest.getBoardId());
    }
}
