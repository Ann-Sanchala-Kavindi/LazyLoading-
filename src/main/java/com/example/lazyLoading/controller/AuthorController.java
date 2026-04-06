package com.example.lazyLoading.controller;

import com.example.lazyLoading.AuthorRepository;
import com.example.lazyLoading.model.Author;
import com.example.lazyLoading.model.Book;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorRepository authorRepository;

    //Method01 - LazyIntializationException
    //Session closes after findById(), Try to accesss books outside the session
    @GetMapping("/authors/{id}/no-lazy")
    public void getLazyError(@PathVariable Long id){
        System.out.println("get authors ");
        Author author=authorRepository.findById(id).orElseThrow();

        System.out.println(" Getting books");
        //Hibernate session close here
        //books are LAZY - not yet loaded -  session is gone
        List<Book> books=author.getBooks();
        System.out.println(books.size()); //<--LazyInitializationException fires Here

    }

    @Transactional
    @GetMapping("/authors/{id}/lazy")
    public void getAuthor(@PathVariable Long id){

        //Hibernate Session opens here
        System.out.println("get authors ");
        Author author=authorRepository.findById(id).orElseThrow();

        System.out.println(" Getting books");
        List<Book> books=author.getBooks();
        System.out.println(books.size());

    }



}
