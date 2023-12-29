package br.com.keepsimple.cadastro.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.keepsimple.cadastro.exception.CepInexistenteException;
import br.com.keepsimple.cadastro.exception.UsuarioExistenteException;
import br.com.keepsimple.cadastro.exception.UsuarioInexistenteException;
import br.com.keepsimple.cadastro.model.Usuario;
import br.com.keepsimple.cadastro.model.request.UsuarioRequest;
import br.com.keepsimple.cadastro.model.response.UsuarioResponse;
import br.com.keepsimple.cadastro.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
    @ResponseBody
    @GetMapping("")
    public ResponseEntity consulta(@RequestBody UsuarioRequest usuarioRequest){
    	Usuario usuario = usuarioService.loadUsuarioByCpf(usuarioRequest.getCpf());
    	if (usuario != null) {
    		return ResponseEntity.ok(UsuarioResponse.fromDomain(usuario));
    	} else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    	}
    }
    
    @ResponseBody
    @PostMapping("")
    public ResponseEntity criarUsuario(@RequestBody UsuarioRequest usuarioRequest){
    	Usuario usuario = null;
		try {
			usuario = usuarioService.criarUsuario(usuarioRequest);
		} catch (Exception e) {
			String mensagemErro = e.getMessage();
			if (e instanceof UsuarioExistenteException) {
				mensagemErro = "Usuário já cadastrado";
			}
			else if (e instanceof CepInexistenteException) {
				mensagemErro = "CEP não cadastrado";
			}			
			else if (e instanceof ParseException) {
				mensagemErro = "Data de Nascimento inválida";
			}
			else if (e instanceof DataIntegrityViolationException) {
				if (e.getMessage().contains("not-null")) {
					mensagemErro = "Campo obrigatório não preenchido";
				}
			}
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
		}
    	UsuarioResponse response = UsuarioResponse.fromDomain(usuario);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PutMapping("")
    public ResponseEntity alterarUsuario(@RequestBody UsuarioRequest usuarioRequest){
    	Usuario usuario = null;
		try {
			usuario = usuarioService.alterarUsuario(usuarioRequest);
		} catch (Exception e) {
			String mensagemErro = e.getMessage();
			if (e instanceof UsuarioInexistenteException) {
				mensagemErro = "Usuário não cadastrado";
			}			
			else if (e instanceof ParseException) {
				mensagemErro = "Data de Nascimento inválida";
			}
			else if (e instanceof DataIntegrityViolationException) {
				if (e.getMessage().contains("not-null")) {
					mensagemErro = "Campo obrigatório não preenchido";
				}
			}
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
		}
    	UsuarioResponse response = UsuarioResponse.fromDomain(usuario);
        return ResponseEntity.ok(response);
    }    

    @ResponseBody
    @DeleteMapping("")
    public ResponseEntity removerUsuario(@RequestBody UsuarioRequest usuarioRequest){
		try {
			usuarioService.removerUsuario(usuarioRequest);
		} catch (Exception e) {
			String mensagemErro = e.getMessage();
			if (e instanceof UsuarioInexistenteException) {
				mensagemErro = "Usuário não cadastrado";
			}			
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
		}
        return ResponseEntity.ok("Usuário removido com sucesso");
    }    
}
