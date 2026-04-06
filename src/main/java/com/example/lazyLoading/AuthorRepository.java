package com.example.lazyLoading;


import com.example.lazyLoading.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {

    // For lazy fix — JOIN FETCH courses
    @Query("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(@Param("id") Long id);


    @EntityGraph(attributePaths = {"books"})
    @Query("FROM Author ")
    List<Author> findAllAuthors();



}
