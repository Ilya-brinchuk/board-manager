package board.manager.demo.model.dto;

import lombok.Data;

@Data
public class ColumnCreateRequestDto {
    private Long boardId;
    private String title;
}
