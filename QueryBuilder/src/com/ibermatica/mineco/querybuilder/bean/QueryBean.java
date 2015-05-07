package com.ibermatica.mineco.querybuilder.bean;

public class QueryBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String query;
	private String args;
	private String argsTypes;
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getArgsTypes() {
		return argsTypes;
	}

	public void setArgsTypes(String argsTypes) {
		this.argsTypes = argsTypes;
	}
}
