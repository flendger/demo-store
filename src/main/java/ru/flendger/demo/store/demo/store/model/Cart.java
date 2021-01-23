package ru.flendger.demo.store.demo.store.model;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.flendger.demo.store.demo.store.dto.ProductDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Component
public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    @Getter
    private int quantity;

    @Getter
    private int sum;


    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public CartItem addItem(ProductDto productDto, int price, int quantity) {
        CartItem item = items.stream()
                .filter(cartItem -> cartItem.getProductDto().equals(productDto))
                .findFirst()
                .orElse(((Supplier<CartItem>) () -> {
                    CartItem newItem = new CartItem(productDto, price, 0);
                    items.add(newItem);
                    return newItem;
                }).get());

        item.setQuantity(quantity);
        countTotals();
        return item;
    }

    public void deleteItem(ProductDto productDto) {
        CartItem item = items.stream()
                .filter(cartItem -> cartItem.getProductDto().equals(productDto))
                .findFirst()
                .orElse(null);
        if (item == null) return;
        items.remove(item);
        countTotals();
    }

    private void countTotals() {
        sum = 0;
        quantity = 0;
        items.forEach(cartItem -> {
            sum += cartItem.getSum();
            quantity += cartItem.getQuantity();
        });
    }
}
