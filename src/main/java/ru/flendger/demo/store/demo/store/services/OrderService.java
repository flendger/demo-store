package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flendger.demo.store.demo.store.dto.OrderDto;
import ru.flendger.demo.store.demo.store.model.Order;
import ru.flendger.demo.store.demo.store.model.User;
import ru.flendger.demo.store.demo.store.repositories.OrderRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<OrderDto> findAllOrderDto() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    public List<OrderDto> findAllByUserOrderDto(User user) {
        return orderRepository.findAllByUser(user)
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order findByIdWithItems(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NoResultException(String.format("Order [%d] not found", id)));
        Hibernate.initialize(order.getOrderItems());
        return order;
    }
}
