package board.manager.demo.controller;

import board.manager.demo.model.Board;
import board.manager.demo.model.Col;
import board.manager.demo.model.Task;
import board.manager.demo.model.dto.ColumnCreateRequestDto;
import board.manager.demo.model.dto.ColumnResponseDto;
import board.manager.demo.model.dto.ColumnUpdateRequestDto;
import board.manager.demo.model.dto.TaskRequestDto;
import board.manager.demo.model.dto.TaskResponseDto;
import board.manager.demo.service.BoardService;
import board.manager.demo.service.ColumnService;
import board.manager.demo.service.MapperToDto;
import board.manager.demo.service.MapperToEntity;
import board.manager.demo.service.TaskService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/columns")
public class ColumnController {
    private final ColumnService columnService;
    private final MapperToDto<Col, ColumnResponseDto> mapperColumnToDto;
    private final BoardService boardService;
    private final TaskService taskService;
    private final MapperToEntity<Task, TaskRequestDto> mapperTaskToEntity;
    private final MapperToDto<Task, TaskResponseDto> mapperTaskToDto;

    public ColumnController(ColumnService columnService,
                            MapperToDto<Col, ColumnResponseDto> mapperColumnToDto,
                            BoardService boardService, TaskService taskService,
                            MapperToEntity<Task, TaskRequestDto> mapperTaskToEntity,
                            MapperToDto<Task, TaskResponseDto> mapperTaskToDto) {
        this.columnService = columnService;
        this.mapperColumnToDto = mapperColumnToDto;
        this.boardService = boardService;
        this.taskService = taskService;
        this.mapperTaskToEntity = mapperTaskToEntity;
        this.mapperTaskToDto = mapperTaskToDto;
    }

    @PostMapping
    public ResponseEntity<ColumnResponseDto> create(
            @RequestBody ColumnCreateRequestDto requestDto) {
        Col column = new Col();
        column.setTitle(requestDto.getTitle());
        Board board = boardService.get(requestDto.getBoardId());
        column.setBoard(board);
        columnService.save(column);
        ColumnResponseDto responseDto = mapperColumnToDto.mapToDto(column);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ColumnResponseDto> update(
            @PathVariable Long id,
            @RequestBody ColumnUpdateRequestDto requestDto) {
        Col column = columnService.get(id);
        Board board = boardService.get(requestDto.getBoardId());
        List<Task> tasks = requestDto.getTasksId()
                .stream()
                .map(taskService::get)
                .collect(Collectors.toList());
        if (requestDto.getTitle() != null) {
            column.setTitle(requestDto.getTitle());
        }
        column.setBoard(board);
        column.setTasks(tasks);
        columnService.save(column);

        ColumnResponseDto responseDto = mapperColumnToDto.mapToDto(column);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ColumnResponseDto> remove(@PathVariable Long id) {
        Col column = columnService.delete(id);
        ColumnResponseDto responseDto = mapperColumnToDto.mapToDto(column);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{columnId}/tasks")
    public ResponseEntity<TaskResponseDto> createTask(
            @PathVariable Long columnId,
            @RequestBody TaskRequestDto requestDto) {
        Task task = mapperTaskToEntity.mapToEntity(requestDto);
        Col column = columnService.get(columnId);
        task.setCol(column);
        taskService.save(task);
        TaskResponseDto responseDto = mapperTaskToDto.mapToDto(task);
        return ResponseEntity.ok(responseDto);
    }
}
