package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.Order;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private int sum;
    private OrderAddressDto orderAddressDto;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.sum = order.getSum();
        this.orderAddressDto = new OrderAddressDto(order.getOrderAddress());
    }
}
