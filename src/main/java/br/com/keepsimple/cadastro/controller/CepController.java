package br.com.keepsimple.cadastro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.keepsimple.cadastro.model.Cep;
import br.com.keepsimple.cadastro.model.request.CepRequest;
import br.com.keepsimple.cadastro.model.response.CepResponse;
import br.com.keepsimple.cadastro.service.CepService;

@Controller
@RequestMapping("/cep")
public class CepController {
	
	@Autowired
	private CepService cepService;
	
    @ResponseBody
    @GetMapping("")
    public ResponseEntity consulta(@RequestBody CepRequest cepRequest){
    	Cep cep = cepService.loadCepByCodigo(cepRequest.getCep());
    	if (cep != null) {
    		return ResponseEntity.ok(CepResponse.fromDomain(cep));
    	} else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CEP não encontrado");
    	}
    }
    
    @ResponseBody
    @PostMapping("")
    public ResponseEntity criacao(@RequestBody CepRequest cepRequest){
    	Cep cep = null;
		try {
			cep = cepService.salvarCep(CepRequest.toDomain(cepRequest));
		} catch (Exception e) {
			String mensagemErro = e.getMessage();
			if (e instanceof DataIntegrityViolationException) {
				if (e.getMessage().contains("not-null")) {
					mensagemErro = "Campo obrigatório não preenchido";
				} else {
					mensagemErro = "CEP já existente";
				}
			}
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
		}
    	CepResponse response = CepResponse.fromDomain(cep);
        return ResponseEntity.ok(response);
    }

}
