package spring.rest.shop.springrestshop.dto.comment;

import lombok.Data;
import spring.rest.shop.springrestshop.entity.Comment;

@Data
public class CommentDTO {
    private long id;

    private long creatorID;
    private String message;


    public CommentDTO (){

    }

    public CommentDTO (Comment comment){
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.creatorID = comment.getCreator().getId();
    }
}
