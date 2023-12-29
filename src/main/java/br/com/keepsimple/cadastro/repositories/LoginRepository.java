package br.com.keepsimple.cadastro.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.keepsimple.cadastro.model.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login, Integer> {
	Login findByEmail(String email);
}