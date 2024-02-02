package br.com.keepsimple.cadastro.model.response;

import br.com.keepsimple.cadastro.model.Cep;

public class CepResponse {
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
    
	public static CepResponse fromDomain(final Cep model) {
		CepResponse response = new CepResponse();
		if (model != null) {
			response.setCep(model.getCep());
			response.setRua(model.getRua());
			response.setBairro(model.getBairro());
			response.setCidade(model.getCidade());
			response.setUf(model.getUf());
		}
		
		return response;
	}
}
