package ru.flendger.demo.store.demo.store.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.flendger.demo.store.demo.store.model.AdditionalText;
import ru.flendger.demo.store.demo.store.model.Order;
import ru.flendger.demo.store.demo.store.repositories.AdditionalTextRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AdditionalTextRepository additionalTextRepository;

    @Test
    public void test() {
        Order order = new Order();
        AdditionalText additionalText = new AdditionalText();
        additionalText.setOrder(order);
        additionalText.setText("test_txt");
        order.setAdditionalTexts(Collections.singletonList(additionalText));

        order = orderService.save(order);

        order = orderService.findById(order.getId());
        assertEquals(1, order.getAdditionalTexts().size());

        additionalText = order.getAdditionalTexts().get(0);

        orderService.clearTextsAndSave(order.getId());

        order = orderService.findById(order.getId());
        assertEquals(0, order.getAdditionalTexts().size());

        additionalText = additionalTextRepository.findById(additionalText.getId()).get();
        assertNull(additionalText.getOrder());
    }
}