package br.com.keepsimple.cadastro.model.request;

import br.com.keepsimple.cadastro.model.Cep;

public class CepRequest {
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;
    
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}

	public static Cep toDomain(final CepRequest request) {
		Cep domain = new Cep();
		domain.setCep(request.getCep());
		domain.setRua(request.getRua());
		domain.setBairro(request.getBairro());
		domain.setCidade(request.getCidade());
		domain.setUf(request.getUf());
		
		return domain;
	}
}
