package board.manager.demo.repository;

import board.manager.demo.model.Col;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Col, Long> {
}
