package com.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graphql.entity.Book;

public interface BookRepo extends JpaRepository<Book,Integer> {

}
