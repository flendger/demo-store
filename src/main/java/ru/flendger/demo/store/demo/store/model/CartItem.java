package ru.flendger.demo.store.demo.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.dto.ProductDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private ProductDto productDto;
    private int quantity;
    private int price;
    private int sum;

    public CartItem(ProductDto productDto, int price, int quantity) {
        this.productDto = productDto;
        this.quantity = quantity;
        this.price = price;
        this.sum = price * quantity;
    }

    public void setPrice(int price) {
        this.price = price;
        countSum();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        countSum();
    }

    private void countSum() {
        this.sum = this.price * this.quantity;
    }
}
