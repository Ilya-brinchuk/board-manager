package board.manager.demo.controller;

import board.manager.demo.model.Task;
import board.manager.demo.model.dto.TaskRequestDto;
import board.manager.demo.model.dto.TaskResponseDto;
import board.manager.demo.service.MapperToDto;
import board.manager.demo.service.MapperToEntity;
import board.manager.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final MapperToEntity<Task, TaskRequestDto> mapperTaskToEntity;
    private final MapperToDto<Task, TaskResponseDto> mapperTaskToDto;

    public TaskController(TaskService taskService,
                          MapperToEntity<Task, TaskRequestDto> mapperTaskToEntity,
                          MapperToDto<Task, TaskResponseDto> mapperTaskToDto) {
        this.taskService = taskService;
        this.mapperTaskToEntity = mapperTaskToEntity;
        this.mapperTaskToDto = mapperTaskToDto;
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDto requestDto) {
        Task task = taskService.get(taskId);
        task.setDescription(requestDto.getDescription());
        task.setTitle(requestDto.getTitle());
        taskService.save(task);
        TaskResponseDto responseDto = mapperTaskToDto.mapToDto(task);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> removeTask(@PathVariable Long taskId) {
        Task task = taskService.get(taskId);
        TaskResponseDto responseDto = mapperTaskToDto.mapToDto(task);
        taskService.delete(taskId);
        return ResponseEntity.ok(responseDto);
    }
}
