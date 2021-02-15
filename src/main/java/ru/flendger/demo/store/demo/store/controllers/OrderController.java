package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.flendger.demo.store.demo.store.bean.Cart;
import ru.flendger.demo.store.demo.store.dto.OrderAddressDto;
import ru.flendger.demo.store.demo.store.dto.OrderDto;
import ru.flendger.demo.store.demo.store.dto.OrderDtoWithItems;
import ru.flendger.demo.store.demo.store.exeptions.ErrorMessage;
import ru.flendger.demo.store.demo.store.exeptions.UnauthorizedException;
import ru.flendger.demo.store.demo.store.model.User;
import ru.flendger.demo.store.demo.store.services.OrderService;
import ru.flendger.demo.store.demo.store.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final Cart cart;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<OrderDto> findAll() {
        return orderService.findAllOrderDto();
    }

    @GetMapping("/user_orders")
    public List<OrderDto> findAllByUserUsername(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("You must login");
        }

        return orderService.findAllByUserUsername(principal.getName());
    }

    @GetMapping("/{id}")
    public Optional<OrderDtoWithItems> findOrderByIdAndUserUsername(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("You must login");
        }

        return orderService.findOrderByIdAndUserUsername(id, principal.getName());
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(Principal principal, @RequestBody(required = false) OrderAddressDto address) {
        if (principal == null) {
            throw new UnauthorizedException("You must login");
        }

        if (address == null || address.getAddress().isBlank()) {
            return new ResponseEntity<>(
                    new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Необходимо заполнить адрес"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UnauthorizedException("You must login"));
        return ResponseEntity.ok(new OrderDto(cart.placeOrder(user, address)));
    }
}
