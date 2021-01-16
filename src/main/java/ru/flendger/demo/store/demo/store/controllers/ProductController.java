package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/page")
    public Page<Product> findPage(@RequestParam Integer page, @RequestParam Integer size) {
        return productService.findAll(page, size);
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
