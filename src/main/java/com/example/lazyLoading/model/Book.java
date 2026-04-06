package com.example.lazyLoading.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String title;

    @ManyToOne
    private Author author;
}
