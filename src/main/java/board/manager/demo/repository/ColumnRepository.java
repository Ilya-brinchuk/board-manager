package board.manager.demo.repository;

import board.manager.demo.model.Column;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    @Query("update Column c set c.title = :title where c.id = :id")
    Optional<Column> updateColumnTitle(Long id, String title);

    Optional<Column> removeColumnById(Long id);
}
