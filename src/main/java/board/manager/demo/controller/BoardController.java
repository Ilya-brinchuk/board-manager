package board.manager.demo.controller;

import board.manager.demo.model.Board;
import board.manager.demo.model.Col;
import board.manager.demo.model.Task;
import board.manager.demo.model.dto.BoardRequestDto;
import board.manager.demo.model.dto.BoardResponseDto;
import board.manager.demo.service.BoardService;
import board.manager.demo.service.ColumnService;
import board.manager.demo.service.MapperToDto;
import board.manager.demo.service.MapperToEntity;
import board.manager.demo.service.TaskService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final MapperToDto<Board, BoardResponseDto> mapperBoardToDto;
    private final MapperToEntity<Board, BoardRequestDto> mapperBoardToEntity;
    private final ColumnService columnService;
    private final TaskService taskService;

    public BoardController(BoardService boardService,
                           MapperToDto<Board, BoardResponseDto> mapperBoardToDto,
                           MapperToEntity<Board, BoardRequestDto> mapperBoardToEntity,
                           ColumnService columnService, TaskService taskService) {
        this.boardService = boardService;
        this.mapperBoardToDto = mapperBoardToDto;
        this.mapperBoardToEntity = mapperBoardToEntity;
        this.columnService = columnService;
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(
            @RequestBody BoardRequestDto boardRequestDto) {
        Board board = mapperBoardToEntity.mapToEntity(boardRequestDto);
        boardService.save(board);
        BoardResponseDto responseDto = mapperBoardToDto.mapToDto(board);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> readBoard(@PathVariable Long id) {
        Board board = boardService.get(id);
        BoardResponseDto responseDto = mapperBoardToDto.mapToDto(board);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/read-full")
    public ResponseEntity<Board> readFullBoard(@PathVariable Long id) {
        Board board = boardService.get(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<BoardResponseDto> responseBoard =
                boardService.getAll(page, size)
                        .stream()
                        .map(mapperBoardToDto::mapToDto)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(responseBoard);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequestDto requestDto) {
        Board board = boardService.get(id);
        if (requestDto.getName() != null) {
            board.setName(requestDto.getName());
        }
        boardService.save(board);
        BoardResponseDto responseDto = mapperBoardToDto.mapToDto(board);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BoardResponseDto> removeBoard(@PathVariable Long id) {
        Board board = boardService.delete(id);
        BoardResponseDto responseDto = mapperBoardToDto.mapToDto(board);
        return ResponseEntity.ok(responseDto);
    }

    @PostConstruct
    public void init() {

        Col byColumn = new Col();
        byColumn.setTitle("Title");

        Task task = new Task();
        task.setTitle("Title");
        task.setDescription("Description");
        taskService.save(task);
        byColumn.setTasks(List.of(task));
        columnService.save(byColumn);
        Board board = new Board();
        board.setName("Bob");
        board.setColumns(List.of(byColumn));
        boardService.save(board);
    }
}
