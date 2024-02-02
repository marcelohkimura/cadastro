package br.com.keepsimple.cadastro.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.keepsimple.cadastro.exception.CepInexistenteException;
import br.com.keepsimple.cadastro.exception.UsuarioExistenteException;
import br.com.keepsimple.cadastro.exception.UsuarioInexistenteException;
import br.com.keepsimple.cadastro.model.Cep;
import br.com.keepsimple.cadastro.model.Endereco;
import br.com.keepsimple.cadastro.model.Login;
import br.com.keepsimple.cadastro.model.Status;
import br.com.keepsimple.cadastro.model.Usuario;
import br.com.keepsimple.cadastro.model.request.UsuarioRequest;
import br.com.keepsimple.cadastro.repositories.CepRepository;
import br.com.keepsimple.cadastro.repositories.EnderecoRepository;
import br.com.keepsimple.cadastro.repositories.LoginRepository;
import br.com.keepsimple.cadastro.repositories.UsuarioRepository;
import br.com.keepsimple.cadastro.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService 
{
	@Autowired
	private CepRepository cepRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private LoginRepository loginRepository;	
	
	@Override
	public Usuario loadUsuarioByCpf(String cpf) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		return usuario;
	}

	@Override
	public Usuario criarUsuario(UsuarioRequest request) throws Exception{
		Cep cep = cepRepository.findByCep(request.getCep());
		if (cep == null) {
			throw new CepInexistenteException();
		}
		Usuario usuario = usuarioRepository.findByCpf(request.getCpf());
		if (usuario != null) {
			throw new UsuarioExistenteException();
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Login login = loginRepository.findByEmail(user.toString());
		
		Endereco endereco = new Endereco();
		endereco.setCep(cep);
		endereco.setNumero(request.getNumero());
		endereco.setComplemento(request.getComplemento());
		endereco = enderecoRepository.save(endereco);
	    
		usuario = new Usuario();
		usuario.setEndereco(endereco);
		usuario.setCpf(request.getCpf());
		usuario.setNome(request.getNome());
		usuario.setNascimento(formatter.parse(request.getNascimento()));
		usuario.setStatus(Status.A);
		usuario.setCriacao(new Date());
		usuario.setLoginCriacao(login);
		
		return(usuarioRepository.save(usuario));
		
	}

	@Override
	public Usuario alterarUsuario(UsuarioRequest request) throws Exception {
		Cep cep = cepRepository.findByCep(request.getCep());
		if (cep == null) {
			throw new CepInexistenteException();
		}
		Usuario usuario = usuarioRepository.findByCpf(request.getCpf());
		if (usuario == null) {
			throw new UsuarioInexistenteException();
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Login login = loginRepository.findByEmail(user.toString());

		Endereco endereco = usuario.getEndereco();
		endereco.setCep(cep);
		endereco.setNumero(request.getNumero());
		endereco.setComplemento(request.getComplemento());
		enderecoRepository.save(endereco);
	    
		usuario.setCpf(request.getCpf());
		usuario.setNome(request.getNome());
		usuario.setNascimento(formatter.parse(request.getNascimento()));
		usuario.setAtualizacao(new Date());
		usuario.setLoginAtualizacao(login);
		if (request.getStatus() != null) {
			Status status = Status.valueOf(request.getStatus());
			usuario.setStatus(status);
			if (status.equals(Status.A)) {
				usuario.setLoginRemocao(null);
				usuario.setRemocao(null);
			}
		}
		
		return(usuarioRepository.save(usuario));

	}

	@Override
	public void removerUsuario(UsuarioRequest request) throws Exception {
		Usuario usuario = usuarioRepository.findByCpf(request.getCpf());
		if (usuario == null) {
			throw new UsuarioInexistenteException();
		}
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Login login = loginRepository.findByEmail(user.toString());
		
		usuario.setStatus(Status.R);
		usuario.setLoginRemocao(login);
		usuario.setRemocao(new Date());
		usuarioRepository.save(usuario);
		
	}
	
}
