package com.literandltx.intership_unit2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@ToString
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group extends BaseEntity {
    @Column(name = "title", unique = true, nullable = false)
    private String title;
}
