package board.manager.demo.service.impl;

import board.manager.demo.model.Col;
import board.manager.demo.repository.ColumnRepository;
import board.manager.demo.service.ColumnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColumnServiceImpl implements ColumnService {
    private final ColumnRepository columnRepository;

    public ColumnServiceImpl(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    @Override
    public Col save(Col col) {
        return columnRepository.save(col);
    }

    @Override
    public Col get(Long id) {
        return columnRepository.getOne(id);
    }

    @Override
    @Transactional
    public Col delete(Long id) {
        Col col = columnRepository.getOne(id);
        columnRepository.delete(col);
        return col;
    }
}
