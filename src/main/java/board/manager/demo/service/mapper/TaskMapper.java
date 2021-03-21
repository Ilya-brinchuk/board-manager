package board.manager.demo.service.mapper;

import board.manager.demo.model.Task;
import board.manager.demo.model.dto.TaskRequestDto;
import board.manager.demo.model.dto.TaskResponseDto;
import board.manager.demo.service.MapperToDto;
import board.manager.demo.service.MapperToEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements MapperToEntity<Task, TaskRequestDto>,
        MapperToDto<Task, TaskResponseDto> {
    @Override
    public Task mapToEntity(TaskRequestDto requestDto) {
        Task task = new Task();
        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        return task;
    }

    @Override
    public TaskResponseDto mapToDto(Task task) {
        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setTaskId(task.getId());
        responseDto.setColumnId(task.getColumn().getId());
        responseDto.setTitle(task.getTitle());
        responseDto.setDescription(task.getDescription());
        return responseDto;
    }
}
