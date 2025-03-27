package com.douglinhas.board.service;

import com.douglinhas.board.model.Board;
import com.douglinhas.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(Board board) {
        if (boardRepository.existsByTitle(board.getTitle())) {
            throw new IllegalArgumentException("Board with the same title already exists.");
        }
            return boardRepository.save(board);
    }

    public Board updateBoard(Long id, Board boardDetails) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(boardDetails.getTitle());
                    board.setDescription(boardDetails.getDescription());
                    return boardRepository.save(board);
                }).orElseThrow(() -> new RuntimeException("Board não encontrado com o ID: " + id));
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
