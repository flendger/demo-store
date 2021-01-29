package ru.flendger.demo.store.demo.store.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceItemId implements Serializable {
    private Long product;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceItemId that = (PriceItemId) o;
        return product.equals(that.product) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, date);
    }
}
