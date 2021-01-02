package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}