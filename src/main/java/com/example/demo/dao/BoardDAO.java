package com.example.demo.dao;

import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper
public interface BoardDAO {
    List<Board> boards(SearchBoardRequest boardRequest);

    Optional<Board> getBoard(Integer boardId);

    Integer getSearchTotalCount(SearchBoardRequest boardRequest);

    Integer createBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Integer boardId);
}
