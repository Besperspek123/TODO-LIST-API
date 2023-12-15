package spring.rest.shop.springrestshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.rest.shop.springrestshop.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
