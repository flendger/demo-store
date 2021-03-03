package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.flendger.demo.store.demo.store.aspects.CountAspect;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CountAspect countAspect;

    @GetMapping("/counter")
    public Map<String, Integer> getRequestCounts() {
        return countAspect.getCounter();
    }
}
