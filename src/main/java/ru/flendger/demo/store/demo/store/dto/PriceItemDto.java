package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import ru.flendger.demo.store.demo.store.entities.PriceItem;

@Data
public class PriceItemDto {
    private final ProductDto productDto;
    private final int price;

    public PriceItemDto(PriceItem priceItem) {
        this.productDto = new ProductDto(priceItem.getProduct());
        this.price = priceItem.getPrice();
    }
}
