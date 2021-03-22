package board.manager.demo.controller;

import board.manager.demo.exception.DataProcessingException;
import board.manager.demo.model.Board;
import board.manager.demo.model.dto.BoardRequestDto;
import board.manager.demo.model.dto.BoardResponseDto;
import board.manager.demo.service.BoardService;
import board.manager.demo.service.MapperToDto;
import board.manager.demo.service.MapperToEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Value("${image.upload.dir}")
    private String uploadDirectory;
    private final BoardService boardService;
    private final MapperToDto<Board, BoardResponseDto> mapperBoardToDto;
    private final MapperToEntity<Board, BoardRequestDto> mapperBoardToEntity;

    public BoardController(BoardService boardService,
                           MapperToDto<Board, BoardResponseDto> mapperBoardToDto,
                           MapperToEntity<Board, BoardRequestDto> mapperBoardToEntity) {
        this.boardService = boardService;
        this.mapperBoardToDto = mapperBoardToDto;
        this.mapperBoardToEntity = mapperBoardToEntity;
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(
            @RequestBody BoardRequestDto boardRequestDto) {
        Board board = mapperBoardToEntity.mapToEntity(boardRequestDto);
        boardService.save(board);
        BoardResponseDto responseDto = mapperBoardToDto.mapToDto(board);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<Void> uploadImage(@PathVariable Long id,
                                            @RequestParam("File") MultipartFile uploadFile) {
        String path = uploadDirectory + uploadFile.getOriginalFilename();

        File file = new File(path);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            file.createNewFile();
            outputStream.write(uploadFile.getBytes());
        } catch (IOException e) {
            throw new DataProcessingException("Can't save image", e);
        }
        Board board = boardService.get(id);
        board.setImagePath(path);
        boardService.save(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/download-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable Long id) throws IOException {
        String path = boardService.get(id).getImagePath();
        File file = new File(path);
        return FileUtils.readFileToByteArray(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> readBoard(@PathVariable Long id) {
        Board board = boardService.get(id);
        BoardResponseDto responseDto = mapperBoardToDto.mapToDto(board);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/read-full")
    public ResponseEntity<Board> readFullBoard(@PathVariable Long id) {
        Board board = boardService.getFull(id);
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
}
