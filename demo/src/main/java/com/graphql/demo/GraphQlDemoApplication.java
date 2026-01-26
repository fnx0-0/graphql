package com.graphql.demo;

import java.io.IOException;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.graphql.service.BookService;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import graphql.schema.idl.errors.SchemaProblem;

@SpringBootApplication
@EntityScan("com.graphql")
@ComponentScan("com.graphql")
@EnableJpaRepositories(basePackages = "com.graphql.repository")
public class GraphQlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphQlDemoApplication.class, args);
    }

    @Bean
    public GraphQL graphQL(BookService bookService)
            throws SchemaProblem, IOException {

        SchemaParser schemaParser = new SchemaParser();

        ClassPathResource schema =
                new ClassPathResource("graphql/schema.graphqls");

        TypeDefinitionRegistry typeRegistry =
                schemaParser.parse(schema.getInputStream());

        RuntimeWiring runtimeWiring =
                RuntimeWiring.newRuntimeWiring()
                        .type(TypeRuntimeWiring.newTypeWiring("Query")
                                .dataFetcher("getBook", bookService.getBook())
                                .dataFetcher("getBooks", bookService.getBooks()))
                        .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();

        GraphQLSchema finalSchema =
                schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

        return GraphQL.newGraphQL(finalSchema).build();
    }

}
