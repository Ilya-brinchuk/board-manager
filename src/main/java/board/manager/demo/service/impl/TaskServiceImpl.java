package board.manager.demo.service.impl;

import board.manager.demo.model.Task;
import board.manager.demo.repository.TaskRepository;
import board.manager.demo.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
    public Task get(Long id) {
        return taskRepository.getOne(id);
    }

    @Override
    @Transactional
    public Task delete(Long id) {
        Task task = taskRepository.getOne(id);
        taskRepository.delete(task);
        return task;
    }
}
