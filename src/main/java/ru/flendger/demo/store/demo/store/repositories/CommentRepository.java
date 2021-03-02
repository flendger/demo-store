package ru.flendger.demo.store.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.flendger.demo.store.demo.store.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long productId);

    @Query(value = "select avg(c.score) as avg_score from Comment c where c.score > 0 and c.product.id = :productId")
    float getAvgScore(@Param("productId") Long product_id);
}
