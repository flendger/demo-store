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
import ru.flendger.demo.store.demo.store.ws.products.ProductXmlDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

    private final static Function<Product, ProductXmlDto> PRODUCT_TO_PRODUCT_XML_DTO = p -> {
        ProductXmlDto pXml = new ProductXmlDto();
        pXml.setId(p.getId());
        pXml.setArticle(p.getArticle());
        pXml.setTitle(p.getTitle());
        pXml.setDescription(p.getDescription());
        pXml.setPrice(p.getPrice());
        return pXml;
    };

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size)).map(ProductDto::new);
    }

    public List<ProductXmlDto> findAllProductXmlDto() {
        return productRepository.findAll().stream().map(PRODUCT_TO_PRODUCT_XML_DTO).collect(Collectors.toList());
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
