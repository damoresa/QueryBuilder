package com.ibermatica.mineco.querybuilder.negocio.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibermatica.mineco.querybuilder.bean.QueryBean;
import com.ibermatica.mineco.querybuilder.exception.QueryBuilderException;
import com.ibermatica.mineco.querybuilder.negocio.QueryBuilderBO;

public class QueryBuilderBOImpl implements QueryBuilderBO {
	
	private static final Logger log = Logger.getLogger(QueryBuilderBOImpl.class);
	
	private String limpiarString(String cadena)
	{
		if (cadena.contains("["))
		{
			cadena = cadena.substring(1);
		}
		
		if (cadena.contains("]"))
		{
			cadena = cadena.substring(0, cadena.length() - 1);
		}
		
		return cadena;
	}
	
	public QueryBean procesarEntrada(String nomFichOrigen)
	{
		File fichOrigen = new File(nomFichOrigen);
		BufferedReader bufferedReader = null;
		
		List<String> lstFilas = new ArrayList<String>();
		
		try
		{
			bufferedReader = new BufferedReader(new FileReader(fichOrigen));
			
			String fila = null;
			
			while ((fila = bufferedReader.readLine()) != null)
			{
				if (!"".equals(fila))
				{
					lstFilas.add(fila);
				}
			}
		}
		catch (IOException ioEx)
		{
			log.error("ERROR: No se pudo procesar el fichero de entrada", ioEx);
			throw new QueryBuilderException("ERROR: No se pudo procesar el fichero de entrada");
		}
		finally
		{
			if (bufferedReader != null)
			{
				try
				{
					bufferedReader.close();
				}
				catch (IOException ioEx) {}
			}
		}
		
		if (lstFilas.size() != 3)
		{
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("ERROR: Número inválido de parámetros. ");
			errorBuilder.append("Recuerde que debe introducir los siguientes valores: \n");
			errorBuilder.append("\t1. Consulta \n");
			errorBuilder.append("\t2. Parametros \n");
			errorBuilder.append("\t3. Tipos de los parámetros \n");
			
			log.error(errorBuilder.toString());
			throw new IllegalArgumentException(errorBuilder.toString());
		}
		
		lstFilas.set(1, this.limpiarString(lstFilas.get(1)));
		lstFilas.set(2, this.limpiarString(lstFilas.get(2)));
		
		QueryBean query = new QueryBean();
		query.setQuery(lstFilas.get(0));
		query.setArgs(lstFilas.get(1));
		query.setArgsTypes(lstFilas.get(2));
		
		return query;
	}

	public QueryBean queryFiller(QueryBean query)
    {
    	int argCounter = 0;
    	int position = 0;
    	
    	do
    	{
    		position = query.getQuery().indexOf('?', position + 1);
    		
    		if (position != -1)
    		{
    			argCounter++;
    		}
    	}
    	while (position != -1);
    	
    	List<String> lstArgs = new ArrayList<String>(Arrays.asList(query.getArgs().split(",")));
    	List<String> lstArgTypes = new ArrayList<String>(Arrays.asList(query.getArgsTypes().split(",")));
    	
    	if (argCounter != lstArgs.size() || argCounter != lstArgTypes.size())
    	{
    		StringBuilder errorBuilder = new StringBuilder();
    		errorBuilder.append("ERROR: Número de parámetros incorrectos. \n");
    		errorBuilder.append("\tParametros en query (?): " + argCounter + " \n");
    		errorBuilder.append("\tParametros de query: " + lstArgs.size() + " \n");
    		errorBuilder.append("\tTipos de parametros: " + lstArgTypes.size() + "\n");
    		
    		log.error(errorBuilder.toString());
    		throw new IllegalArgumentException(errorBuilder.toString());
    	}
    	else
    	{
    		for (int counter = 0; counter < argCounter; counter++)
    		{
    			String argValue = lstArgs.get(counter).trim();
    			String argType = lstArgTypes.get(counter).trim();
    			
    			// 07/05/2015
    			// Caso excepcional para Torres, que pone tipos nulos
    			// Si el tipo es nulo, forzamos a que el valor también lo sea
    			if (!"null".equals(argType))
    			{
    				try
    				{
						Class<?> javaClass = Class.forName(argType);
		    			Object argInstance = null;
		    			
		    			try
		    			{
		        			Constructor<?> classConstructor = javaClass.getConstructor(String.class);
		        			argInstance = classConstructor.newInstance(argValue);
		    			}
		    			catch (NoSuchMethodException nsmEx)
		    			{
		        			log.error("ERROR: No se pudo encontrar el constructor parametrizado para " + javaClass.getName());
		    				argInstance = javaClass.newInstance();
		    			}
		    			
		    			if (argInstance instanceof String)
		    			{
		    				argValue = '\'' + argValue + '\'';
		    			}
    				}
    				catch (ClassNotFoundException cnfEx)
    				{
    					log.error("ERROR: No se pudo encontrar la clase.");
    					throw new QueryBuilderException("ERROR: No se pudo encontrar la clase.");
    				}
    				catch (IllegalAccessException iaEx)
    				{
    					log.error("ERROR: Acceso no permitido.");
    					throw new QueryBuilderException("ERROR: Acceso no permitido.");
    				}
    				catch (InstantiationException iEx)
    				{
    					log.error("ERROR: No se pudo instanciar la clase");
    					throw new QueryBuilderException("ERROR: No se pudo instanciar la clase");
    				}
    				catch (InvocationTargetException itEx)
    				{
    					log.error("ERROR: No se pudo invocar al constructor parametrizado");
    					throw new QueryBuilderException("ERROR: No se pudo invocar al constructor parametrizado");
    				}
    			}
    			else
    			{
    				argValue = argType;
    			}
    			
    			query.setQuery(query.getQuery().replaceFirst("\\?", argValue));
    		}
    	}
    	
    	return query;
	}

	public boolean procesarSalida(String nomFichDestino, QueryBean query)
	{
		File fichDestino = new File(nomFichDestino);
		BufferedWriter bufferedWriter = null;
		
		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter(fichDestino));
			
			bufferedWriter.write(query.getQuery());
		}
		catch (IOException ioEx)
		{
			log.error("ERROR: No se pudo generar el fichero de salida", ioEx);
			throw new QueryBuilderException("ERROR: No se pudo generar el fichero de salida");
		}
		finally
		{
			if (bufferedWriter != null)
			{
				try
				{
					bufferedWriter.close();
				}
				catch (IOException ioEx) {}
			}
		}
		
		return false;
	}
}
