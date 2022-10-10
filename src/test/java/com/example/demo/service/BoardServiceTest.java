package com.example.demo.service;

import com.example.demo.dao.BoardDAO;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.exception.BoardException;
import com.example.demo.model.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("보드 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    //SUT: System Under Test, 테스트 대상 시스템, 테스트를 하려는 대상.
    @InjectMocks private BoardService sut;

    @Mock private BoardDAO boardDAO;

    @DisplayName("검색어 없이 게시글 리스트를 조회한다.")
    @Test
    void givenNoSearchParameters_whenSearchingBoards_thenReturnsBoard() {
        //given
        SearchBoardRequest noSearchParameters = noSearchParameters();
        given(boardDAO.boards(noSearchParameters)).willReturn(List.of());

        //when
        List<BoardDto> boards = sut.boards(noSearchParameters);

        //then
        assertThat(boards).isEmpty();
        then(boardDAO).should().boards(noSearchParameters);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingBoards_thenReturnsBoard() {
        //given
        SearchBoardRequest searchBoardRequest = searchParameters();
        given(boardDAO.boards(searchBoardRequest)).willReturn(List.of());

        //when
        List<BoardDto> boards = sut.boards(searchBoardRequest);

        //then
        assertThat(boards).isEmpty();
        then(boardDAO).should().boards(searchBoardRequest);
    }

    @DisplayName("게시글 ID로 게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenBoardId_whenSearchingBoard_thenReturnsBoard() {
        //given
        Integer boardId = 1;
        Board board = createBoard();
        given(boardDAO.getBoard(boardId)).willReturn(Optional.of(board));

        //when
        BoardDto boardDto = sut.getBoard(boardId);

        //then
        assertThat(boardDto)
                .hasFieldOrPropertyWithValue("boardId", board.getBoardId())
                .hasFieldOrPropertyWithValue("boardTitle", board.getBoardTitle())
                .hasFieldOrPropertyWithValue("boardContent", board.getBoardContent())
                .hasFieldOrPropertyWithValue("boardUserName", board.getBoardUserName())
                .hasFieldOrPropertyWithValue("boardPassword", board.getBoardPassword())
                .hasFieldOrPropertyWithValue("createAt", board.getCreateAt())
                .hasFieldOrPropertyWithValue("deleteYN", board.getDeleteYN());
        then(boardDAO).should().getBoard(boardId);
    }

    @DisplayName("게시글이 없으면 예외를 던진다.")
    @Test
    void givenNonexistentBoardId_whenSearchingBoard_thenThrowsException() {
        //given
        Integer boardId = 1;
        given(boardDAO.getBoard(boardId)).willReturn(Optional.empty());

        //when
        Throwable t = catchThrowable(() -> sut.getBoard(boardId));

        //then
        assertThat(t)
                .isInstanceOf(BoardException.class)
                .hasMessage("%s번 게시글을 찾을 수 없습니다.", boardId);
        then(boardDAO).should().getBoard(boardId);
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenBoardInfo_whenCreateBoard_thenCreateBoard() {
        //given
        Integer boardId = 1;
        BoardDto boardDto = createBoardDto();
        given(boardDAO.createBoard(any(Board.class))).willReturn(boardId);

        //when
        Integer createBoardId = sut.createBoard(boardDto);

        //then
        assertThat(createBoardId).isEqualTo(boardDto.getBoardId());
        then(boardDAO).should().createBoard(any(Board.class));
    }

    @DisplayName("게시글 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenUpdateBoardInfo_whenUpdatingBoard_thenUpdateBoard() {
        //given
        Integer boardId = 1;
        Board board = createBoard();
        BoardDto boardDto = createBoardDto();
        given(boardDAO.getBoard(boardId)).willReturn(Optional.of(board));
        willDoNothing().given(boardDAO).updateBoard(boardDto.toEntity());

        //when
        sut.updateBoard(boardDto.getBoardId(), boardDto);

        //then
        assertThat(board)
                .hasFieldOrPropertyWithValue("boardId", boardDto.getBoardId())
                .hasFieldOrPropertyWithValue("boardTitle", boardDto.getBoardTitle())
                .hasFieldOrPropertyWithValue("boardContent", boardDto.getBoardContent())
                .hasFieldOrPropertyWithValue("boardUserName", boardDto.getBoardUserName())
                .hasFieldOrPropertyWithValue("boardPassword", boardDto.getBoardPassword());
        then(boardDAO).should().getBoard(boardId);
        then(boardDAO).should().updateBoard(boardDto.toEntity());
    }

    @DisplayName("게시글 ID와 비밀번호를 입력하면, 게시글을 삭제한다.")
    @Test
    void givenBoardIdAndPassword_whenDeletingBoard_thenDeleteBoard() {
        //given
        Integer boardId = 1;
        String password = "1234";
        Board board = createBoard();
        given(boardDAO.getBoard(boardId)).willReturn(Optional.of(board));
        willDoNothing().given(boardDAO).deleteBoard(boardId);

        //when
        sut.deleteBoard(boardId, password);

        //then
        then(boardDAO).should().deleteBoard(boardId);
    }

    @DisplayName("게시글 ID와 틀린 비밀번호를 입력하면, 예외를 던진다.")
    @Test
    void givenBoardIdAndWrongPassword_whenDeletingBoard_thenDeleteBoard() {
        //given
        Integer boardId = 1;
        String password = "12345";
        Board board = createBoard();
        given(boardDAO.getBoard(boardId)).willReturn(Optional.of(board));

        //when
        Throwable t = catchThrowable(() -> sut.deleteBoard(boardId, password));

        //then
        assertThat(t)
                .isInstanceOf(BoardException.class)
                .hasMessage("%s은 틀린 패스워드 입니다.", password);
        then(boardDAO).should().getBoard(boardId);
    }

    private Board createBoard() {
        return Board.builder()
                .boardId(1)
                .boardTitle("제목")
                .boardContent("내용")
                .boardUserName("김지수")
                .boardPassword("1234")
                .createAt(LocalDateTime.now())
                .deleteYN("N")
                .build();
    }

    private BoardDto createBoardDto() {
        BoardDto dto = BoardDto.builder()
                .boardId(1)
                .boardTitle("제목")
                .boardContent("내용")
                .boardUserName("김지수")
                .boardPassword("1234")
                .build();
        return dto;
    }

    private SearchBoardRequest noSearchParameters() {
        return new SearchBoardRequest();
    }

    private SearchBoardRequest searchParameters() {
        SearchBoardRequest searchBoardRequest = new SearchBoardRequest();
        searchBoardRequest.setSearchType("boardTitle");
        searchBoardRequest.setSearchValue("제목");
        return searchBoardRequest;
    }

}