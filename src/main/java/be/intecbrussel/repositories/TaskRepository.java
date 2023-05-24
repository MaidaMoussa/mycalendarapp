package be.intecbrussel.repositories;

import be.intecbrussel.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "Select t from Task t where  t.startDate>= :date and t.endDate <= :date ")
    List<Task> findAllByDate(@Param("date") LocalDate date);
}
