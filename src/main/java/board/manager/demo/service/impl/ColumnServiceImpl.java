package board.manager.demo.service.impl;

import board.manager.demo.exception.DataProcessingException;
import board.manager.demo.model.Column;
import board.manager.demo.repository.ColumnRepository;
import board.manager.demo.service.ColumnService;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl implements ColumnService {
    private final ColumnRepository columnRepository;

    public ColumnServiceImpl(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    @Override
    public Column save(Column column) {
        return columnRepository.save(column);
    }

    @Override
    public Column updateTitle(Long id, String title) {
        return columnRepository.updateColumnTitle(id, title).orElseThrow(() ->
                new DataProcessingException("Can't update column by this id: " + id));
    }

    @Override
    public Column delete(Long id) {
        return columnRepository.removeColumnById(id).orElseThrow(() ->
                new DataProcessingException("Can't delete column by this id: " + id));
    }
}
