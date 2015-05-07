package com.ibermatica.mineco.querybuilder.negocio;

import com.ibermatica.mineco.querybuilder.bean.QueryBean;

public interface QueryBuilderBO {
	
	public QueryBean procesarEntrada(String nomFichOrigen);

	public QueryBean queryFiller(QueryBean query);
	
	public boolean procesarSalida(String nomFichDestino, QueryBean query);
}
