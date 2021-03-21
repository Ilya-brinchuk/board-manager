package board.manager.demo.service;

import board.manager.demo.model.Col;

public interface ColumnService {
    Col save(Col byColumn);

    Col get(Long id);

    Col delete(Long id);
}
