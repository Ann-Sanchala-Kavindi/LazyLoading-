package com.example.lazyLoading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
class Book {
    @Id
    @GeneratedValue
    Long id;

    String title;

    @ManyToOne
    Author author;
}
