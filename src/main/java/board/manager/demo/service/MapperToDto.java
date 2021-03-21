package board.manager.demo.service;

public interface MapperToDto<E, T> {
    T mapToDto(E entity);
}
