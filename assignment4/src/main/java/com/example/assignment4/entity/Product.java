package com.example.assignment4.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    long id;
    String name;
    float price;
    String category;
}
