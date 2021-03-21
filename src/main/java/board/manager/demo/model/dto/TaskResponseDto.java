package board.manager.demo.model.dto;

import lombok.Data;

@Data
public class TaskResponseDto {
    private Long taskId;
    private Long columnId;
    private String title;
    private String description;
}
