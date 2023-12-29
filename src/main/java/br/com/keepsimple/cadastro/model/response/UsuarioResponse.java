package br.com.keepsimple.cadastro.model.response;

import java.text.SimpleDateFormat;

import br.com.keepsimple.cadastro.model.Usuario;

public class UsuarioResponse {
	private String cpf;
    private String nome;
    private String nascimento;
	private String cep;
    private String rua;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String status;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
	public static UsuarioResponse fromDomain(final Usuario model) {
		UsuarioResponse response = new UsuarioResponse();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (model != null) {
			response.setCpf(model.getCpf());
			response.setNome(model.getNome());
			response.setNascimento(formatter.format(model.getNascimento()));
			response.setCep(model.getEndereco().getCep().getCep());
			response.setRua(model.getEndereco().getCep().getRua());
			response.setNumero(model.getEndereco().getNumero());
			response.setComplemento(model.getEndereco().getComplemento());
			response.setBairro(model.getEndereco().getCep().getBairro());
			response.setCidade(model.getEndereco().getCep().getCidade());
			response.setUf(model.getEndereco().getCep().getUf());
			response.setStatus(model.getStatus().getDescricao());
		}
		
		return response;
	}
}
