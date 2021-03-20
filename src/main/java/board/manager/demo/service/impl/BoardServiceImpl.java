package board.manager.demo.service.impl;

import board.manager.demo.exception.DataProcessingException;
import board.manager.demo.model.Board;
import board.manager.demo.repository.BoardRepository;
import board.manager.demo.service.BoardService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Board get(Long id) {
        return boardRepository.getOne(id);
    }

    @Override
    public Board getFull(Long id) {
        return boardRepository.findByIdWithRelations(id).orElseThrow(() ->
                new DataProcessingException("Can't find board by this id: " + id));
    }

    @Override
    public List<Board> getAll(int page, int size) {
        Sort sort = Sort.by("name").ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return boardRepository.getAllBoard(pageable);
    }

    @Override
    public Board updateName(Long id, String name) {
        return boardRepository.updateName(id, name).orElseThrow(() ->
                new DataProcessingException("Can't update board by this id: " + id));
    }

    @Override
    public Board delete(Long id) {
        return boardRepository.removeBoardById(id).orElseThrow(() ->
                new DataProcessingException("Can't delete board by this id: " + id));
    }
}
