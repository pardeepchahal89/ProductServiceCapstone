package com.codewithpardeep.productservicecapstone.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends Base {
    private double price;
    private String description;
    private double weight;
    private String imageUrl;

    @ManyToOne
    private Category category;

}
