package com.douglinhas.board;

import com.douglinhas.board.controller.BoardController;
import com.douglinhas.board.model.Board;
import com.douglinhas.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testCreateBoard() throws Exception {
        String boardJson = "{\"title\":\"Board Test\", \"description\":\"Test Description\"}";

        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(boardJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Board Test"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetBoards() throws Exception {
        Board board = new Board(null, "Test Board", "Test Description");
        boardRepository.save(board);

        mockMvc.perform(get("/api/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Board"))
                .andExpect(jsonPath("$[0].description").value("Test Description"));
    }
}
