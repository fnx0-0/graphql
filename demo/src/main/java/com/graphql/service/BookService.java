package com.graphql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.entity.Book;
import com.graphql.repository.resolver.BookResolver;

import graphql.schema.DataFetcher;

@Service
public class BookService {

    @Autowired
    private BookResolver repoResolver;

    public DataFetcher<Book> getBook(){
        return env ->{
             int bookId = env.getArgument("id");
             return repoResolver.getBook(bookId);
        };

     }

    
     public DataFetcher<List<Book>> getBooks(){
        return env ->{
             return repoResolver.getBooks();
        };

     }

}
