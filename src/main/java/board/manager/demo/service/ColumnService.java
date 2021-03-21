package board.manager.demo.service;

import board.manager.demo.model.Column;

public interface ColumnService {
    Column save(Column byColumn);

    Column get(Long id);

    Column updateTitle(Long id, String title);

    Column delete(Long id);
}
