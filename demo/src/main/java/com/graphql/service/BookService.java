package com.graphql.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.entity.Author;
import com.graphql.entity.Book;
import com.graphql.repository.resolver.BookResolver;
import com.graphql.repository.resolver.AuthorResolver;

import graphql.schema.DataFetcher;

@Service
public class BookService {

   @Autowired
   private BookResolver bookRepoResolver;

   @Autowired
   private AuthorResolver authorRepoResolver;


    public DataFetcher<Book> getBook(){
        return env ->{
               String bookId = env.getArgument("id");
               UUID bookIdUuid = UUID.fromString(bookId);
               return bookRepoResolver.getBook(bookIdUuid);
        };

     }

    
     public DataFetcher<List<Book>> getBooks(){
        return env ->{
             return bookRepoResolver.getBooks();
        };

     }


   @SuppressWarnings("unchecked")
   public DataFetcher<Book> createBook(){

         return env->{
            LinkedHashMap<String,Object> a = env.getArgument("book");
            Map<String,Object> author = null;


            Book book =null;
            if(a.get("name") != null && a.get("pages") != null){
               book = Book.builder().name((a.get("name")).toString()).pages((Integer)a.get("pages")).build();
            }

            if(a.get("author") != null){
                author = (Map<String,Object>)a.get("author");
            }
            System.out.println("auhtor is : " + author);

            
            Book afterSaveBook = bookRepoResolver.newBookEntry(book);

            if(author != null && author.get("name") != null && author.get("age") != null){
               Author newAuthor = Author.builder().age((Integer)author.get("age")).book(afterSaveBook).name(author.get("name").toString()).build();
               authorRepoResolver.createAuthor(newAuthor);
            }

            return afterSaveBook;
         };
     }

}
