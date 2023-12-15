package spring.rest.shop.springrestshop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.entity.Comment;
import spring.rest.shop.springrestshop.repository.CommentRepository;

@Service
public class CommentService {

    public final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }
}
