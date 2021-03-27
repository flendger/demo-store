package ru.flendger.demo.store.demo.store.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.flendger.demo.store.demo.store.model.Product;
import ru.flendger.demo.store.demo.store.repositories.CommentRepository;
import ru.flendger.demo.store.demo.store.repositories.ProductRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest(classes = {ProductService.class})
@Slf4j
@ActiveProfiles("test")
class ProductServiceTest {
    @MockBean
    public CommentRepository commentRepository;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void doBeforeEach() {
        Product p1 = new Product();
        p1.setId(1L);

        Mockito
                .doReturn(Optional.of(p1))
                .when(productRepository)
                .findById(1L);
    }

    @Test
    void findById() {
        log.warn("Product.id = 1L");
        Optional<Product> productOptional1L = productService.findById(1L);
        Assertions.assertNotNull(productOptional1L.get());

        log.warn("Product.id = 100L");
        Assertions.assertThrows(NoSuchElementException.class, () -> productService.findById(100L).get());
    }
}