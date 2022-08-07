package com.demo.graphql.model;

import java.util.Map;

public class GraphqlRequestBody {

	private String queries;
	private String operationNames;
	private Map<String, Object> variables;

	public String getQueries() {
		return queries;
	}

	public void setQueries(String queries) {
		this.queries = queries;
	}

	public String getOperationNames() {
		return operationNames;
	}

	public void setOperationNames(String operationNames) {
		this.operationNames = operationNames;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

}
