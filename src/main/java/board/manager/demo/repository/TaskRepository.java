package board.manager.demo.repository;

import board.manager.demo.model.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> removeTaskById(Long id);
}
