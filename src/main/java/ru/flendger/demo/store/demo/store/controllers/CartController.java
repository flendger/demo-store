package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.flendger.demo.store.demo.store.dto.CartDto;
import ru.flendger.demo.store.demo.store.dto.CartItemDto;
import ru.flendger.demo.store.demo.store.bean.Cart;
import ru.flendger.demo.store.demo.store.exeptions.ResourceNotFoundException;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.services.ProductService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final Cart cart;
    private final ProductService productService;

    @GetMapping
    public CartDto getCart() {
        return new CartDto(cart);
    }

    @GetMapping("/add")
    public CartItemDto addItem(@RequestParam Long id,
                            @RequestParam(defaultValue = "1") Integer quantity) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id [%d] not found", id)));
        return new CartItemDto(cart.addItem(product, product.getPrice(), quantity));
    }

    @GetMapping("/remove")
    public CartItemDto removeItem(@RequestParam Long id,
                                  @RequestParam(defaultValue = "1") Integer quantity) {
        return cart.removeItem(id, quantity).map(CartItemDto::new).orElse(null);
    }

    @GetMapping("/delete")
    public void deleteItem(@RequestParam Long id) {
        cart.deleteItem(id);
    }
}
