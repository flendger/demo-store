package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.Product;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String article;
    private String title;
    private int price;
    private float score;

    public ProductDto(Product product) {
        formProduct(product);
    }

    public ProductDto formProduct(Product product) {
        this.id = product.getId();
        this.article = product.getArticle();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.score = product.getScore();
        return this;
    }
}
