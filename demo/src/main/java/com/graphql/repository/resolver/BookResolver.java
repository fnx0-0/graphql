package com.graphql.repository.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.graphql.entity.Book;
import com.graphql.repository.BookRepo;

@Repository
public class BookResolver {
    
    @Autowired
    private BookRepo repo;

    public Book getBook(int id){
        return repo.findById(id).orElse(new Book());
    }

     public List<Book> getBooks(){
        return repo.findAll();
    }
}
