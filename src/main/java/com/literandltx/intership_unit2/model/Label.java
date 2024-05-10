package com.literandltx.intership_unit2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "labels")
public class Label extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
}