package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Menu extends PanacheEntity {
    private Long restaurant;
    private Integer active;

    @Transient
    private List<MenuItem> menuItems;
}
