package com.ibermatica.mineco.querybuilder.negocio;

import com.ibermatica.mineco.querybuilder.bean.QueryBean;

/**
 * <p>Interfaz del servicio de parseo de <i>queries</i>.</p>
 * 
 * @author damores
 *
 */
public interface QueryBuilderBO {
	
	/**
	 * <p>Método que procesa la entrada, a raíz del fichero origen con 
	 * el nombre indicado, convirtiéndola en un objeto Java.</p>
	 * 
	 * @param nomFichOrigen <code>String</code> con el nombre del fichero 
	 * origen que contiene los datos de la consulta a parsear.
	 * @return <code>QueryBean</code> con los datos de la consulta.
	 * 
	 * @see QueryBean
	 */
	public QueryBean procesarEntrada(String nomFichOrigen);
	
	/**
	 * <p>Método que sustituye los parámetros sobre la consulta.</p>
	 * 
	 * @param query <code>QueryBean</code> con los datos de la consulta y sus 
	 * parámetros.
	 * @return <code>QueryBean</code> cuyo atributo <code>query</code> ya contiene 
	 * la query con los parámetros sustituidos.
	 */
	public QueryBean queryFiller(QueryBean query);
	
	/**
	 * <p>Método que genera la salida de la aplicación, en un fichero con el nombre 
	 * indicado.</p>
	 * 
	 * @param nomFichDestino <code>String</code> con el nombre del fichero en el que 
	 * se generará la consulta resultado del parseo.
	 * @param query <code>QueryBean</code> con los datos de la consulta que se ha 
	 * parseado.
	 */
	public void procesarSalida(String nomFichDestino, QueryBean query);
}
