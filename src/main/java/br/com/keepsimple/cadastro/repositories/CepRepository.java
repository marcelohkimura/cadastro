package br.com.keepsimple.cadastro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.keepsimple.cadastro.model.Cep;

@Repository
public interface CepRepository extends JpaRepository<Cep, Integer> {
	Cep findByCep(String cep); 
}
