package ru.flendger.demo.store.demo.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flendger.demo.store.demo.store.model.AdditionalText;

public interface AdditionalTextRepository extends JpaRepository<AdditionalText, Long> {
}
