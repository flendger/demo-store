package ru.flendger.demo.store.demo.store.bean;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.flendger.demo.store.demo.store.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class Cart {
    private List<CartItem> items;
    @Getter
    private int quantity;
    @Getter
    private int sum;


    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @PostConstruct
    private void init() {
        this.items = new ArrayList<>();
    }

    public CartItem addItem(Product product, int price, int quantity) {
        CartItem item = items.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartItem(product, price, 0);
            items.add(item);
        }

        item.setQuantity(item.getQuantity() + quantity);
        countTotals();
        return item;
    }

    public Optional<CartItem> removeItem(Long id, Integer quantity) {
        CartItem item = items.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(id))
                .findFirst()
                .orElse(null);

        if (item == null) {
            return Optional.empty();
        }

        int newQuantity = item.getQuantity() - quantity;
        if (newQuantity <= 0) {
            items.remove(item);
            countTotals();
            return Optional.empty();
        }
        item.setQuantity(newQuantity);
        countTotals();
        return Optional.of(item);
    }

    public void deleteItem(Long id) {
        CartItem item = items.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(id))
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
