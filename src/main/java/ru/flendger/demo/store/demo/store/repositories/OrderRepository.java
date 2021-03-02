package ru.flendger.demo.store.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flendger.demo.store.demo.store.dto.OrderDtoWithItems;
import ru.flendger.demo.store.demo.store.model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserUsername(String username);

    Optional<OrderDtoWithItems> findOrderByIdAndUserUsername(Long id, String username);
}
