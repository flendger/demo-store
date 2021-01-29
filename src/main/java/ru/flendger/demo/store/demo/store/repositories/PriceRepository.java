package ru.flendger.demo.store.demo.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.flendger.demo.store.demo.store.entities.PriceItem;
import ru.flendger.demo.store.demo.store.entities.PriceItemId;

import java.time.LocalDate;

@Repository
public interface PriceRepository extends JpaRepository<PriceItem, PriceItemId>, JpaSpecificationExecutor<PriceItem> {
    @Query(value = "SELECT * FROM PRICES prices INNER JOIN " +
            "(SELECT product_id, MAX(date) AS maxDate FROM prices WHERE date<=:date GROUP BY product_id) maxTable " +
            "ON prices.product_id = maxTable.product_id AND prices.date = maxTable.maxDate ORDER BY product_id",
    countQuery = "SELECT count(*) FROM (SELECT MAX(date) AS maxDate FROM prices WHERE date<=:date GROUP BY product_id)",
    nativeQuery = true)
    Page<PriceItem> findAllOnDate(Pageable pageable, @Param("date") LocalDate date);

    @Override
//    @Query(value = "SELECT * FROM PRICES prices INNER JOIN " +
//            "(SELECT product_id, MAX(date) AS maxDate FROM prices WHERE date<='2020-01-28' GROUP BY product_id) maxTable " +
//            "ON prices.product_id = maxTable.product_id AND prices.date = maxTable.maxDate ORDER BY product_id",
//            countQuery = "SELECT count(*) FROM (SELECT MAX(date) AS maxDate FROM prices WHERE date<='2020-01-28' GROUP BY product_id)",
//            nativeQuery = true)
    Page<PriceItem> findAll(Specification<PriceItem> spec, Pageable pageable);
}
