package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.flendger.demo.store.demo.store.dto.PriceItemDto;
import ru.flendger.demo.store.demo.store.repositories.specifications.PriceSpecification;
import ru.flendger.demo.store.demo.store.services.PriceService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping
    public Page<PriceItemDto> getPrices(@RequestParam MultiValueMap<String, String> params,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "1000") Integer size) {
        return priceService.findAllOnDate(PriceSpecification.build(params), PageRequest.of(page, size), LocalDate.now());
    }
}
