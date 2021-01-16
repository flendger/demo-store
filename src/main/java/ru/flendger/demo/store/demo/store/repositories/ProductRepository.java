package ru.flendger.demo.store.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flendger.demo.store.demo.store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
