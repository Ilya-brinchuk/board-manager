package board.manager.demo.service.impl;

import board.manager.demo.exception.DataProcessingException;
import board.manager.demo.model.Task;
import board.manager.demo.repository.TaskRepository;
import board.manager.demo.service.TaskService;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task delete(Long id) {
        return taskRepository.removeTaskById(id).orElseThrow(() ->
                new DataProcessingException("Can't delete task by this id: " + id));
    }
}
