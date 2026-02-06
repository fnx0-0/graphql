package com.graphql.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EntityScan("com.graphql")
@ComponentScan("com.graphql")
@EnableJpaRepositories(basePackages = "com.graphql.repository")
public class GraphQlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphQlDemoApplication.class, args);
    }

}
