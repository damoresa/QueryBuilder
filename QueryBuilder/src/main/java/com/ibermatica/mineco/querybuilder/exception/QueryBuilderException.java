package com.ibermatica.mineco.querybuilder.exception;

/**
 * <p>Excepción empleada para controlar errores en la lógica 
 * de la aplicación.</p>
 * <p>Extiende de <code>RuntimeException</code> para evitar tener 
 * que declararla en todos los métodos de negocio.
 * 
 * @author damores
 *
 */
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
