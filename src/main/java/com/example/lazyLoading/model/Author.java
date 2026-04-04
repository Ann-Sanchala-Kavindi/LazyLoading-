package com.example.lazyLoading.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue
    Long id;

    String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    List<Book> books;

}
