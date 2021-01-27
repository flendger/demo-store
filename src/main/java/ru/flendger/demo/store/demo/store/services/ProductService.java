package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.flendger.demo.store.demo.store.dto.ProductDto;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size)).map(ProductDto::new);
    }

    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto saveOrUpdate(ProductDto productDto) {
        Product product;
        if (productDto.getId() == null) {
            product = new Product();
        } else {
            product = productRepository.findById(productDto.getId()).orElse(new Product());
        }
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());

        return productDto.formProduct(productRepository.save(product));
    }
}
