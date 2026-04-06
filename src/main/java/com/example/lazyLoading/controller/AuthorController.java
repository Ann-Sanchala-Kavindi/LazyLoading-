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


    private final AuthorRepository authorRepository;

    //-----Method01 - LazyIntializationException----------------------------------------------------------
    //Session closes after findById(), Try to access books outside the session
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

    @Transactional(readOnly = true)
    @GetMapping("/authors/{id}/lazy")
    public void getAuthor(@PathVariable Long id){

        //Hibernate Session opens here
        System.out.println("get authors ");
        Author author=authorRepository.findById(id).orElseThrow();

        System.out.println(" Getting books");
        List<Book> books=author.getBooks();
        System.out.println(books.size());

    }




    //---Method 02 -Correct Lazy Loading--------------------------------------------------------
    //Uses JOIN FETCH query — loads courses WITHIN the session
    @GetMapping("/authors/{id}/books")
    public void getAuthorWithBooks(@PathVariable Long id){
        System.out.println("get author");

        //Fetches author+books in ONE Query(session still open)
        Author author=authorRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new RuntimeException("Author Not Found"));

        System.out.println("Getting Books");
        // books already loaded - no extra query, no exception
        var books=author.getBooks();

        System.out.println(books.size());

    }


    // METHOD 3 — All Authors, each with their books(N+1 issue)--------------------------------
    //@Transactional(readOnly = true) - not needed
    @GetMapping("/authors/books/n+1issue")
    public void getAllAuthorsWithBooksIssue(){

        System.out.println("Getting the Authors");
        List<Author> authors=authorRepository.findAll();

        System.out.println("Getting each author books");
        for(Author author:authors){
            System.out.println(author.getBooks().size());
        }
    }

    // METHOD 4 — All Authors, each with their books(N+1 issue fixed)
    //@Transactional(readOnly = true) - not needed
    @GetMapping("/authors/books/n+1fixed")
    public void getAllAuthorsWithBooksFixed() {

        System.out.println("getting the authors");
        List<Author> authors = authorRepository.findAllAuthors(); // only student rows

        System.out.println("getting each authors books");
        for (Author author:authors) {
            System.out.println(author.getBooks().size());
        }
    }





}
