package com.graphql.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

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
               String bookId = env.getArgument("id");
               UUID bookIdUuid = UUID.fromString(bookId);
               return repoResolver.getBook(bookIdUuid);
        };

     }

    
     public DataFetcher<List<Book>> getBooks(){
        return env ->{
             return repoResolver.getBooks();
        };

     }


     public DataFetcher<Book> createBook(){

         return env->{
            LinkedHashMap<String,Object> a = env.getArgument("book");
            System.out.println("book parameters name : " + a.get("name") + " pages : " + a.get("pages"));
            Book book = Book.builder().name((a.get("name")).toString()).pages((Integer)a.get("pages")).build();
            return repoResolver.newBookEntry(book);
         };
     }

}
