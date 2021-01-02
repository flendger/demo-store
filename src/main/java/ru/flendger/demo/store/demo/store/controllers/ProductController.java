package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/add")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }
}
