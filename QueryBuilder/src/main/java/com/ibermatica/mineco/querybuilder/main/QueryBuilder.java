package com.ibermatica.mineco.querybuilder.main;

import org.apache.log4j.Logger;

import com.ibermatica.mineco.querybuilder.bean.QueryBean;
import com.ibermatica.mineco.querybuilder.exception.QueryBuilderException;
import com.ibermatica.mineco.querybuilder.negocio.QueryBuilderBO;
import com.ibermatica.mineco.querybuilder.negocio.impl.QueryBuilderBOImpl;

/**
 * <p>Clase principal que lanza el programa.</p>
 * <p>Como no se ha usado <code>Spring</code>, se inicializa el servicio 
 * mediante el constructor estático.</p>
 * <p>La aplicación requiere de los siguientes argumentos para su correcto 
 * funcionamiento:
 * <ul>
 * 	<li><b>Nombre fichero origen</b>: con el nombre del fichero desde el que se leerá 
 * la traza de log con los datos de la consulta.</li>
 * 	<li><b>Nombre fichero destino</b>: con el nombre del fichero en el que se generará 
 * el resultado de la aplicación.</li>
 * </ul>
 * </p>
 * 
 * @author damores
 *
 */
public class QueryBuilder {
	
	private static final Logger log = Logger.getLogger(QueryBuilder.class);
	
	private static QueryBuilderBO queryBuilderBO;
	
	// FIXME: Con Spring esto no sería necesario.
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
