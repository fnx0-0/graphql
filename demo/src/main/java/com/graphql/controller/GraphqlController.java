package com.graphql.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphql.model.GraphqlRequestBody;

import org.springframework.web.bind.annotation.RequestBody;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;


@RestController
@RequestMapping("graphql-base/v1")
public class GraphqlController {

    @Autowired
    private GraphQL graphql;

   @PostMapping(value="graphql")
    public CompletableFuture<Map<String,Object>> execute(@RequestBody GraphqlRequestBody body) {

        Map<String, Object> variables =
            body.getVariables() != null
                    ? body.getVariables()
                    : new HashMap<>();
                    
        ExecutionInput input = ExecutionInput.newExecutionInput()
                .query(body.getQuery())
                .operationName(body.getOperationName())
                .variables(variables)
                .build();

        return graphql.executeAsync(input).thenApply(ExecutionResult::toSpecification);

        
    }
}
