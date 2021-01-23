package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.flendger.demo.store.demo.store.dto.ProductDto;
import ru.flendger.demo.store.demo.store.exeptions.ResourceNotFoundException;
import ru.flendger.demo.store.demo.store.model.Cart;
import ru.flendger.demo.store.demo.store.model.CartItem;
import ru.flendger.demo.store.demo.store.services.ProductService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final Cart cart;
    private final ProductService productService;

    @GetMapping
    public Cart getCart() {
        return this.cart;
    }

    @GetMapping("/add")
    public CartItem addItem(@RequestParam Long id,
                            @RequestParam(defaultValue = "1") Integer quantity) {
        ProductDto productDto = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id [%d] not found", id)));
        return cart.addItem(productDto, productDto.getPrice(), quantity);
    }

    @GetMapping("/delete")
    public void deleteItem(@RequestParam Long id) {
        ProductDto productDto = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id [%d] not found", id)));
        cart.deleteItem(productDto);
    }
}
