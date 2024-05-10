package com.literandltx.intership_unit2.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "rank")
    private Double rank;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "cartItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Label> labels = new LinkedHashSet<>();
}
