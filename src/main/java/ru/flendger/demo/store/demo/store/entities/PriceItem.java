package ru.flendger.demo.store.demo.store.entities;

import lombok.Data;
import ru.flendger.demo.store.demo.store.model.Product;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@IdClass(PriceItemId.class)
@Table(name = "prices")
@Data
public class PriceItem {
    @Id
    private LocalDate date;

    @ManyToOne
    @Id
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "price")
    private int price;

    @Override
    public String toString() {
        return "PriceItem{" +
                "date=" + date +
                ", productId=" + product.getId() +
                ", price=" + price +
                '}';
    }
}
