package br.com.keepsimple.cadastro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.keepsimple.cadastro.model.Cep;
import br.com.keepsimple.cadastro.repositories.CepRepository;
import br.com.keepsimple.cadastro.service.CepService;

@Service
public class CepServiceImpl implements CepService 
{
	@Autowired
	private CepRepository cepRepository;
	
	@Override
	public Cep loadCepByCodigo(String codigo) {
		Cep cep = cepRepository.findByCep(codigo);
		return cep;
	}

	@Override
	public Cep salvarCep(Cep cep) {
		return(cepRepository.save(cep));
		
	}
	
}
