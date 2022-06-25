package ru.flendger.demo.store.demo.store.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "additional_texts")
@Data
public class AdditionalText {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "text")
    private String text;
}
