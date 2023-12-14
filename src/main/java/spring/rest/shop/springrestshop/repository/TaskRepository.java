package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findById(long id);
    List<Task> findByCreator(User user);
    List<Task> findByExecutors(User user);

}
