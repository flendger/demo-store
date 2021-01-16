package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.flendger.demo.store.demo.store.dto.ProductDto;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public Page<ProductDto> findAll(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        return new PageImpl<>(productPage.getContent().stream()
                                                                            .map(ProductDto::new)
                                                                            .collect(Collectors.toList()),
                                                        productPage.getPageable(),
                                                        productPage.getTotalElements());
    }

    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto save(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(product.getPrice());
        return productDto.setByProduct(productRepository.save(product));
    }
}
