package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import ru.flendger.demo.store.demo.store.bean.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private final List<CartItemDto> items;
    private final int quantity;
    private final int sum;

    public CartDto(Cart cart) {
        this.quantity = cart.getQuantity();
        this.sum = cart.getSum();

        items = cart.getItems()
                .stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
    }
}
