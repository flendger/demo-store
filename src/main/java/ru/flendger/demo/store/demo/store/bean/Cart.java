package ru.flendger.demo.store.demo.store.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.flendger.demo.store.demo.store.dto.OrderAddressDto;
import ru.flendger.demo.store.demo.store.exeptions.CartEmptyException;
import ru.flendger.demo.store.demo.store.model.*;
import ru.flendger.demo.store.demo.store.services.OrderService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class Cart {
    private final OrderService orderService;

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

    public void clear() {
        items.clear();
        countTotals();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Order placeOrder(User user, OrderAddressDto orderAddressDto) {
        if (isEmpty()) {
            throw new CartEmptyException("The cart is empty");
        }

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(user);
        order.setSum(sum);
        order.setOrderAddress(new OrderAddress(user, orderAddressDto.getAddress()));

        order.setOrderItems(new ArrayList<>());

        items.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setSum(cartItem.getSum());
            order.getOrderItems().add(orderItem);
        });

        clear();
        return orderService.save(order);
    }
}
