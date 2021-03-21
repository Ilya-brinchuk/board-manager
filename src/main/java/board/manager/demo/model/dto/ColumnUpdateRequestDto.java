package board.manager.demo.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class ColumnUpdateRequestDto {
    private String title;
    private Long boardId;
    private List<Long> tasksId;
}
