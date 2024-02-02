package br.com.keepsimple.cadastro.service;

import br.com.keepsimple.cadastro.model.Usuario;
import br.com.keepsimple.cadastro.model.request.UsuarioRequest;

public interface UsuarioService {

	Usuario loadUsuarioByCpf(String cpf);
	Usuario criarUsuario(UsuarioRequest request) throws Exception;
	Usuario alterarUsuario(UsuarioRequest request) throws Exception;
	void removerUsuario(UsuarioRequest request) throws Exception;
}
