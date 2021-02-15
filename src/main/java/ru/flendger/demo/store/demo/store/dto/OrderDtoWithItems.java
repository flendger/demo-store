package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDtoWithItems {
    private Long id;
    private LocalDateTime date;
    private int sum;
    private OrderAddressDto orderAddressDto;
    private List<OrderItemDto> orderItemDtos;

    public OrderDtoWithItems(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.sum = order.getSum();
        this.orderAddressDto = new OrderAddressDto(order.getOrderAddress());
        this.orderItemDtos = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
