package ru.flendger.demo.store.demo.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.flendger.demo.store.demo.store.dto.PriceItemDto;
import ru.flendger.demo.store.demo.store.entities.PriceItem;
import ru.flendger.demo.store.demo.store.repositories.PriceRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public Page<PriceItemDto> findAllOnDate(Specification<PriceItem> spec, Pageable pageable, LocalDate date) {
        return priceRepository.findAll(spec, pageable).map(PriceItemDto::new);
    }
}
