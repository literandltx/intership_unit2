package com.literandltx.intership_unit2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@ToString
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
}
