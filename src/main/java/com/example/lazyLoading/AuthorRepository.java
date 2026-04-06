package com.example.lazyLoading;


import com.example.lazyLoading.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {


}
