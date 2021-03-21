package board.manager.demo.service.mapper;

import board.manager.demo.model.Col;
import board.manager.demo.model.dto.ColumnResponseDto;
import board.manager.demo.service.MapperToDto;
import org.springframework.stereotype.Component;

@Component
public class ColumnMapper implements MapperToDto<Col, ColumnResponseDto> {
    @Override
    public ColumnResponseDto mapToDto(Col column) {
        ColumnResponseDto responseDto = new ColumnResponseDto();
        responseDto.setColumnId(column.getId());
        responseDto.setTitle(column.getTitle());
        return responseDto;
    }
}
