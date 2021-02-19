package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.Comment;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private ProductDto productDto;
    private String username;
    private String comment;
    private int score;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.productDto = new ProductDto(comment.getProduct());
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.score = comment.getScore();
    }
}
