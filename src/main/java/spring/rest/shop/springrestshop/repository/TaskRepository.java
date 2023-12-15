package spring.rest.shop.springrestshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findById(long id);

    Page<Task> findByExecutors(User executor, Pageable pageable);
    Page<Task> findByCreator(User creator, Pageable pageable);


}
