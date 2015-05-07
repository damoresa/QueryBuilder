package com.ibermatica.mineco.querybuilder.main;

import org.apache.log4j.Logger;

import com.ibermatica.mineco.querybuilder.bean.QueryBean;
import com.ibermatica.mineco.querybuilder.exception.QueryBuilderException;
import com.ibermatica.mineco.querybuilder.negocio.QueryBuilderBO;
import com.ibermatica.mineco.querybuilder.negocio.impl.QueryBuilderBOImpl;

public class QueryBuilder {
	
	private static final Logger log = Logger.getLogger(QueryBuilder.class);
	
	private static QueryBuilderBO queryBuilderBO;
	
	static
	{
		queryBuilderBO = new QueryBuilderBOImpl();
	}

	public static void main(String[] args) {
		
		if (args.length != 2)
		{
			log.error("ERROR: Número de argumentos inválido.");
		}
		else
		{
			String nomFichOrigen = args[0];
			String nomFichDestino = args[1];
			
			try
			{
				QueryBean query = queryBuilderBO.procesarEntrada(nomFichOrigen);
				query = queryBuilderBO.queryFiller(query);
				queryBuilderBO.procesarSalida(nomFichDestino, query);
			}
			catch (QueryBuilderException qbEx)
			{
				log.error(qbEx);
			}
		}
	}

}
