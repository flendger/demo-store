package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flendger.demo.store.demo.store.dto.CommentDto;
import ru.flendger.demo.store.demo.store.exeptions.ResourceNotFoundException;
import ru.flendger.demo.store.demo.store.model.Comment;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.model.User;
import ru.flendger.demo.store.demo.store.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;

    public List<CommentDto> findAllByProductId(Long productId) {
        return commentRepository.findAllByProductId(productId)
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto save(CommentDto commentDto, String username) {
        Comment comment;
        if (commentDto.getId() == null) {
            comment = new Comment();
        } else {
            comment = commentRepository
                    .findById(commentDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Comment with id[%d] not found", commentDto.getId())));
        }

        if (commentDto.getProductDto() == null || commentDto.getProductDto().getId() == null) {
            throw new NullPointerException("Can't save comment without product");
        }

        Product product = productService
                .findById(commentDto.getProductDto().getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id[%d] not found", commentDto.getProductDto().getId())));

        if (username == null || username.isBlank()) {
            throw new NullPointerException("Can't save comment without user");
        }

        User user = userService
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Username [%s] not found", username)));

        comment.setUsername(commentDto.getUsername());
        comment.setScore(commentDto.getScore());
        comment.setComment(commentDto.getComment());
        comment.setProduct(product);
        comment.setUser(user);

        CommentDto newCommentDto = new CommentDto(commentRepository.save(comment));
        //обновляем средний рейтинг
        productService.save(product);

        return newCommentDto;
    }

    public float getAvgScore(Long product_id) {
        return commentRepository.getAvgScore(product_id);
    }
}
