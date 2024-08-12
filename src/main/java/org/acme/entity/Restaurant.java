package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Restaurant extends PanacheEntity {
    private String name;
    private String location;

    @Column(name = "type_name")
    private String type;
}
