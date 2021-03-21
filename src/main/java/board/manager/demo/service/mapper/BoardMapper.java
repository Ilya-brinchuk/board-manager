package board.manager.demo.service.mapper;

import board.manager.demo.model.Board;
import board.manager.demo.model.dto.BoardRequestDto;
import board.manager.demo.model.dto.BoardResponseDto;
import board.manager.demo.service.MapperToDto;
import board.manager.demo.service.MapperToEntity;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper implements MapperToDto<Board, BoardResponseDto>,
        MapperToEntity<Board, BoardRequestDto> {
    @Override
    public BoardResponseDto mapToDto(Board board) {
        BoardResponseDto responseDto = new BoardResponseDto();
        responseDto.setBoardId(board.getId());
        responseDto.setBoardName(board.getName());
        return responseDto;
    }

    @Override
    public Board mapToEntity(BoardRequestDto requestDto) {
        Board board = new Board();
        board.setName(requestDto.getName());
        return board;
    }
}
