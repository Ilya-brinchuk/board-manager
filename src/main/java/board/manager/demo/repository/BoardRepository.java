package board.manager.demo.repository;

import board.manager.demo.model.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b inner join fetch b.cols where b.id = :id")
    Optional<Board> findByIdWithRelations(@Param("id") Long id);

    @Query("update Board b set b.name = :name where b.id = :id")
    Optional<Board> updateName(@Param("id") Long id, @Param("name") String name);

    @Query("select b from Board b")
    List<Board> getAllBoard(Pageable pageable);
}
