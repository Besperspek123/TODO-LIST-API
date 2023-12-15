package spring.rest.shop.springrestshop.dto.comment;


import lombok.Data;
import spring.rest.shop.springrestshop.entity.Comment;

@Data
public class CommentCreateDTO {
    private String message;

    public CommentCreateDTO (){

    }

    public CommentCreateDTO (Comment comment){
        this.message = comment.getMessage();
    }
}
