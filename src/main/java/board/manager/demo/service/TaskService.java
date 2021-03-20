package board.manager.demo.service;

import board.manager.demo.model.Task;

public interface TaskService {
    Task save(Task task);

    Task delete(Long id);
}
