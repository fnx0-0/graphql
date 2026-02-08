package com.graphql.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.graphql.service.AuthorService;
import com.graphql.service.BookService;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import graphql.schema.idl.errors.SchemaProblem;

@Configuration
public class GraphQLConfig {

        @Bean
        public GraphQL graphQL(BookService bookService,AuthorService authorService)
                throws SchemaProblem, IOException {

                SchemaParser schemaParser = new SchemaParser();

                ClassPathResource schema =
                        new ClassPathResource("graphql/schema.graphqls");

                TypeDefinitionRegistry typeRegistry =
                        schemaParser.parse(schema.getInputStream());

                RuntimeWiring runtimeWiring;
                runtimeWiring = RuntimeWiring.newRuntimeWiring()
                        .type(newTypeWiring("Query")
                                .dataFetcher("getBook", bookService.getBook())
                                .dataFetcher("getBooks", bookService.getBooks()))
                        .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                                .dataFetcher("createBook", bookService.createBook()))
                        .type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("author", authorService.getAuthor()))
                        .build();

                SchemaGenerator schemaGenerator = new SchemaGenerator();

                GraphQLSchema finalSchema =
                        schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

                return GraphQL.newGraphQL(finalSchema).build();
        }

}
