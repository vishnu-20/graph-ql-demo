package com.demo.graphql.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.graphql.entity.EmployeeEntity;

import com.demo.graphql.service.EmployeeService;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;


@RestController
public class GraphqlController {

	
	private GraphQL graphQL;
	

	@Value("classpath:employee.graphqls")
	private Resource resource;

	@Autowired
	private EmployeeService employeeService;
	@PostConstruct
	public void readSchema() throws IOException {
		File file = resource.getFile();

		TypeDefinitionRegistry definitionRegistry = new SchemaParser().parse(file);
		RuntimeWiring runtimeWiring = buildwiring();
		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(definitionRegistry, runtimeWiring);
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();

	}


	private RuntimeWiring buildwiring() {
		DataFetcher<List<EmployeeEntity>> findAll = data -> {
			return (List<EmployeeEntity>) employeeService.getRec();
		};

		DataFetcher<EmployeeEntity> findByEmail = data -> {
			return (EmployeeEntity) employeeService.findByEmailId(data.getArgument("email"));
		};
		return RuntimeWiring.newRuntimeWiring().type("Query", typeWiring -> typeWiring
				.dataFetcher("getAllEmployee", findAll).dataFetcher("findPersonByEmail", findByEmail)).build();

	}

	@PostMapping("/save")
	private String savedata(@RequestBody EmployeeEntity employeeEntity) {

		return employeeService.insertRec(employeeEntity);
	}

	@GetMapping("/get")
	private List<EmployeeEntity> getData() {

		return employeeService.getRec();
	}

	@PostMapping("/getData")
	private ResponseEntity<Object> qraphqlendpoint(@RequestBody String query) {

		ExecutionResult executionResult = graphQL.execute(query);
		return new ResponseEntity<Object>(executionResult, HttpStatus.OK);
	}
	
	@PostMapping("/getEmail")
	private ResponseEntity<Object> getEmail(@RequestBody String query) {

		ExecutionResult executionResult = graphQL.execute(query);
		return new ResponseEntity<Object>(executionResult, HttpStatus.OK);
	}

//	@PostMapping(value = "qraphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public void execute(@RequestBody GraphqlRequestBody body) {
//
//		graphQL.executeAsync(ExecutionInput.newExecutionInput().query(body.getQueries())
//				.operationName(body.getOperationNames()).variables(body.getVariables()).build());
//	}
}
