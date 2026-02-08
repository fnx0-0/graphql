package com.graphql.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graphql.entity.Author;
import com.graphql.entity.Book;


public interface AuthorRepo extends JpaRepository<Author, UUID> {

    Author findByBook(Book book);
}
