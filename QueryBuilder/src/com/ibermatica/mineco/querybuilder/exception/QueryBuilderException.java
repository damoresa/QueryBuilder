package com.ibermatica.mineco.querybuilder.exception;

public class QueryBuilderException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public QueryBuilderException()
	{
		super();
	}
	
	public QueryBuilderException(String message)
	{
		super(message);
	}
	
	public QueryBuilderException(Throwable th)
	{
		super(th);
	}
	
	public QueryBuilderException(String message, Throwable th)
	{
		super(message, th);
	}
}
