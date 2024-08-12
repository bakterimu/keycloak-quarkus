package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {
    private Long restaurantId;
    private BigDecimal total;
    @Transient
    private List<OrderItem> orderItems;
}
