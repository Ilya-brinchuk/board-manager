package board.manager.demo.service;

import board.manager.demo.model.Board;
import java.util.List;

public interface BoardService {
    Board save(Board board);

    Board get(Long id);

    Board getFull(Long id);

    List<Board> getAll(int page, int size);

    Board updateName(Long id, String name);

    Board delete(Long id);
}
