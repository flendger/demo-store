package ru.flendger.demo.store.demo.store.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class CountAspect {
    private Map<String, Integer> counter;

    @PostConstruct
    private void init() {
        counter = new HashMap<>();
    }

    public Map<String, Integer> getCounter() {
        return Collections.unmodifiableMap(counter);
    }

    @Before("execution(public * ru.flendger.demo.store.demo.store.*.*.*(..))")
    public void countSignature (JoinPoint joinPoint) {
        counter.put(joinPoint.getSignature().toShortString(), counter.getOrDefault(joinPoint.getSignature().toShortString(), 0) + 1);
    }
}
