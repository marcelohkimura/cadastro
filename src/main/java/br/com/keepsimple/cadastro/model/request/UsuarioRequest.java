package br.com.keepsimple.cadastro.model.request;

public class UsuarioRequest {
    private String cpf;
    private String nome;
    private String nascimento;
    private String status;
    private String cep;
    private Integer numero;
    private String complemento;

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

    public String getStatus() {
		return status;
	}

    public void setStatus(String status) {
		this.status = status;
	}

    public String getCep() {
		return cep;
	}

    public void setCep(String cep) {
		this.cep = cep;
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
    
}
