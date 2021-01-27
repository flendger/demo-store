package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.flendger.demo.store.demo.store.dto.ProductDto;
import ru.flendger.demo.store.demo.store.exeptions.ResourceNotFoundException;
import ru.flendger.demo.store.demo.store.repositories.specifications.ProductSpecification;
import ru.flendger.demo.store.demo.store.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findProductDtoById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product with id [%d] not found", id)));
    }

    @GetMapping
    public Page<ProductDto> findPage(@RequestParam MultiValueMap<String, String> params,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "1000") Integer size) {
        return productService.findAll(ProductSpecification.build(params), page, size);
    }

    @PutMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        //todo: add error
        return productService.saveOrUpdate(productDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto add(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        //todo: add error
        return productService.saveOrUpdate(productDto);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        productService.deleteById(id);
    }
}