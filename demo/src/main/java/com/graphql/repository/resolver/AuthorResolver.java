package com.graphql.repository.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.graphql.entity.Author;
import com.graphql.entity.Book;
import com.graphql.repository.AuthorRepo;

@Repository
public class AuthorResolver {

    @Autowired
    private AuthorRepo repo;

    public Author createAuthor(Author author){

        return repo.save(author);
    }


    public Author getAuthor(Book book){

        return repo.findByBook(book);
    }

}
