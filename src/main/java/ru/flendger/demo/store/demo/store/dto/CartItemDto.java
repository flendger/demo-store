package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import ru.flendger.demo.store.demo.store.bean.CartItem;

@Data
public class CartItemDto {
    private final ProductDto productDto;
    private final int quantity;
    private final int price;
    private final int sum;

    public CartItemDto(CartItem item) {
        this.productDto = new ProductDto(item.getProduct());
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.sum = item.getSum();
    }
}
