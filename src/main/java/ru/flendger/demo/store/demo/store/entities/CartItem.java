package ru.flendger.demo.store.demo.store.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.flendger.demo.store.demo.store.dto.ProductDto;
import ru.flendger.demo.store.demo.store.model.Product;

@Data
@NoArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;
    private int price;
    private int sum;

    public CartItem(Product product, int price, int quantity) {
        this.product = product;
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
