package br.com.keepsimple.cadastro.service;

import br.com.keepsimple.cadastro.model.Cep;

public interface CepService {

	Cep loadCepByCodigo(String codigo);
	Cep salvarCep(Cep cep) throws Exception;
}
