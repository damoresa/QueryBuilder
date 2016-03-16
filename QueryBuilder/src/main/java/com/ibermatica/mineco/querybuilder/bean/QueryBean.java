package com.ibermatica.mineco.querybuilder.bean;

/**
 * <p>Clase que modela los datos de una consulta.</p>
 * 
 * @author damores
 *
 */
public class QueryBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * <code>String</code> con la cadena de la consulta.
	 */
	private String query;
	
	/**
	 * <code>String</code> con la cadena de los parámetros de la consulta.
	 */
	private String args;
	
	/**
	 * <code>String</code> de los tipos de cada parámetro de la consulta.
	 */
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
