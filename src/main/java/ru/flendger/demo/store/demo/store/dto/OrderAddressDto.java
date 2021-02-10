package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.model.OrderAddress;

@Data
@NoArgsConstructor
public class OrderAddressDto {
    private String address;

    public OrderAddressDto(OrderAddress orderAddress) {
        this.address = orderAddress.getAddress();
    }
}
