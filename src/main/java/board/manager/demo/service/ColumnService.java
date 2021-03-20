package board.manager.demo.service;

import board.manager.demo.model.Column;

public interface ColumnService {
    Column save(Column column);

    Column updateTitle(Long id, String title);

    Column delete(Long id);
}
