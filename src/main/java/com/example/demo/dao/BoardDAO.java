package com.example.demo.dao;

import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.dto.request.BoardFormRequest;
import com.example.demo.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BoardDAO {
    List<Board> boards(SearchBoardRequest boardRequest);

    Board getBoard(Integer boardId);

    Integer getSearchTotalCount(SearchBoardRequest boardRequest);

    Integer createBoard(BoardFormRequest boardFormRequest);

    void updateBoard(BoardFormRequest boardFormRequest);

    void deleteBoard(Integer boardId);
}
