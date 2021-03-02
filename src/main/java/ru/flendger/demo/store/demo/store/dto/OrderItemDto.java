package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.OrderItem;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private ProductDto productDto;
    private int quantity;
    private int price;
    private int sum;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.productDto = new ProductDto(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.sum = orderItem.getSum();
    }
}
