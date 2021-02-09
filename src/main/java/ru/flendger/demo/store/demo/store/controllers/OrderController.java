package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.flendger.demo.store.demo.store.bean.Cart;
import ru.flendger.demo.store.demo.store.dto.OrderDto;
import ru.flendger.demo.store.demo.store.exeptions.UnauthorizedException;
import ru.flendger.demo.store.demo.store.model.User;
import ru.flendger.demo.store.demo.store.services.OrderService;
import ru.flendger.demo.store.demo.store.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final Cart cart;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    //todo: доступ только для админов + для обычного пользователя реализовать метод поиска только своих заказов
    public List<OrderDto> findAll() {
        return orderService.findAllOrderDto();
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("You must login");
        }
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UnauthorizedException("You must login"));
        return ResponseEntity.ok(new OrderDto(cart.placeOrder(user)));
    }
}
