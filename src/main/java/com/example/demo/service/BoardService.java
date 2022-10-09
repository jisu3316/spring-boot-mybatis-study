package com.example.demo.service;

import com.example.demo.dao.BoardDAO;
import com.example.demo.dto.BoardDto;
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

    public List<BoardDto> boards(SearchBoardRequest boardRequest) {
        return boardDAO.boards(boardRequest).stream().map(BoardDto::from).collect(Collectors.toList());
    }

    public BoardDto getBoard(Integer boardId) {
        Board board = boardDAO.getBoard(boardId).orElseThrow(() -> new BoardException(ErrorCode.BOARD_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", boardId)));

        return BoardDto.from(board);
    }

    public Integer getSearchTotalCount(SearchBoardRequest boardRequest) {
        return boardDAO.getSearchTotalCount(boardRequest);
    }

    public Integer createBoard(BoardDto boardDto) {
        Integer boardId = boardDAO.createBoard(boardDto.toEntity());
        return boardId;

    }

    public Integer updateBoard(Integer boardId, BoardDto boardDto) {
        Board board = boardDAO.getBoard(boardId).orElseThrow(() -> new BoardException(ErrorCode.BOARD_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", boardId)));

        if (!board.getBoardPassword().equals(boardDto.getBoardPassword())) {
            log.info("boardPassword {} ", board.getBoardPassword());
            log.info("requestPassword {} ", boardDto.getBoardPassword());
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", boardDto.getBoardPassword()));
        }
        boardDAO.updateBoard(boardDto.toEntity());
        return board.getBoardId();
    }

    public void deleteBoard(Integer boardId, String inputPassword) {
        Board board = boardDAO.getBoard(boardId).orElseThrow(() -> new BoardException(ErrorCode.BOARD_NOT_FOUND, String.format("%s번 게시글을 찾을 수 없습니다.", boardId)));

        if (!board.getBoardPassword().equals(inputPassword)) {
            log.info("boardPassword {} ", board.getBoardPassword());
            log.info("requestPassword {} ", inputPassword);
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", inputPassword));
        }
        boardDAO.deleteBoard(board.getBoardId());
    }
}
