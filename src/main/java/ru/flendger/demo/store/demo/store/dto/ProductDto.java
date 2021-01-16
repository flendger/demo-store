package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.Product;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private int price;

    public ProductDto(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
    }

    public ProductDto setByProduct(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        return this;
    }
}
