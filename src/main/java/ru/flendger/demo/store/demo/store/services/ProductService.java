package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flendger.demo.store.demo.store.dto.ProductDto;
import ru.flendger.demo.store.demo.store.exeptions.ResourceNotFoundException;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.repositories.CommentRepository;
import ru.flendger.demo.store.demo.store.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size)).map(ProductDto::new);
    }

    public Optional<ProductDto> findProductDtoById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto saveDto(ProductDto productDto) {
        Product product;
        if (productDto.getId() == null) {
            product = new Product();
        } else {
            product = productRepository
                    .findById(productDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id[%d] not found", productDto.getId())));
        }
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());

        return productDto.formProduct(save(product));
    }

    public Product save(Product product) {
        if (product.getId() != null) {
            product.setScore(commentRepository.getAvgScore(product.getId()));
        }
        return productRepository.save(product);
    }
}
