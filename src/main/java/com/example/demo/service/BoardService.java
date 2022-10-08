package com.example.demo.service;

import com.example.demo.dao.BoardDAO;
import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.dto.request.BoardFormRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.exception.BoardException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDAO boardDAO;

    public List<BoardResponse> boards(SearchBoardRequest boardRequest) {
        return boardDAO.boards(boardRequest).stream().map(BoardResponse::from).collect(Collectors.toList());
    }

    public BoardResponse getBoard(Integer boardId) {
        Board board = boardDAO.getBoard(boardId);
        if (board == null) {
            throw new BoardException(ErrorCode.BOARD_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", boardId));
        }
        return BoardResponse.from(boardDAO.getBoard(boardId));
    }

    public Integer getSearchTotalCount(SearchBoardRequest boardRequest) {
        return boardDAO.getSearchTotalCount(boardRequest);
    }

    public Integer createBoard(BoardFormRequest boardFormRequest) {
        return boardDAO.createBoard(boardFormRequest);
    }

    public void updateBoard(Integer boardId, BoardFormRequest boardFormRequest) {
        Board board = boardDAO.getBoard(boardId);
//        if (board == null) {
//            throw new BoardException(ErrorCode.BOARD_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", boardId));
//        }
        if (!board.getBoardPassword().equals(boardFormRequest.getBoardPassword())) {
            log.info("boardPassword {} ", board.getBoardPassword());
            log.info("requestPassword {} ", boardFormRequest.getBoardPassword());
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", boardFormRequest.getBoardPassword()));
        }
        boardDAO.updateBoard(boardFormRequest);
    }

    public void deleteBoard(Integer boardId, String inputPassword) {
        Board board = boardDAO.getBoard(boardId);
        if (!board.getBoardPassword().equals(inputPassword)) {
            log.info("boardPassword {} ", board.getBoardPassword());
            log.info("requestPassword {} ", inputPassword);
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", inputPassword));
        }
        boardDAO.deleteBoard(board.getBoardId());
    }
}
