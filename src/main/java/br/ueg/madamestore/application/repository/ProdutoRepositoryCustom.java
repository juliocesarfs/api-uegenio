/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import java.util.List;

import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Produto;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * 
 * @author UEG
 */
public interface ProdutoRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Produto} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroProdutoDTO
	 * @return
	 */
	public List<Produto> findAllByFiltro(FiltroProdutoDTO filtroProdutoDTO);



}
