package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flendger.demo.store.demo.store.dto.OrderDto;
import ru.flendger.demo.store.demo.store.dto.OrderDtoWithItems;
import ru.flendger.demo.store.demo.store.model.Order;
import ru.flendger.demo.store.demo.store.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDto> findAllByUserUsername(String username) {
        return orderRepository.findAllByUserUsername(username)
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderDto> findAllOrderDto() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<OrderDtoWithItems> findOrderByIdAndUserUsername(Long id, String username) {
        return orderRepository.findOrderByIdAndUserUsername(id, username);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
