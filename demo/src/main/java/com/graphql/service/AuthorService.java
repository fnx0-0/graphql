package com.graphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.entity.Author;
import com.graphql.entity.Book;
import com.graphql.repository.resolver.AuthorResolver;

import graphql.schema.DataFetcher;

@Service
public class AuthorService {

    @Autowired
    private AuthorResolver repo;

    public DataFetcher<Author> createAuthor(){

        return env ->{
            Author author = env.getArgument("author");
            return repo.createAuthor(author);
        };
        
    }// dummy create 



    public DataFetcher<Author> getAuthor(){
        return env->{
            Book book = env.getSource();
            return repo.getAuthor(book);
        };
    }

}
