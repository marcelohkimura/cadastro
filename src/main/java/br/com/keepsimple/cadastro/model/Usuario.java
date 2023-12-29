package br.com.keepsimple.cadastro.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column( nullable = false )
    private String cpf;
	
	@Column( nullable = false )	
    private String nome;
	
	@Column( nullable = false )
    private Date nascimento;
	
	@Column( nullable = false )
	private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_login_criacao", referencedColumnName = "id")	
    private Login loginCriacao;
    
	@Column(name = "data_criacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date criacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_login_atualizacao", referencedColumnName = "id")	
    private Login loginAtualizacao;
	
	@Column(name = "data_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizacao;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_login_remocao", referencedColumnName = "id")	
    private Login loginRemocao;
	
	@Column(name = "data_remocao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date remocao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")	
    private Endereco endereco;

    public Usuario() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Login getLoginCriacao() {
		return loginCriacao;
	}

	public void setLoginCriacao(Login loginCriacao) {
		this.loginCriacao = loginCriacao;
	}

	public Date getCriacao() {
		return criacao;
	}

	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}

	public Login getLoginAtualizacao() {
		return loginAtualizacao;
	}

	public void setLoginAtualizacao(Login loginAtualizacao) {
		this.loginAtualizacao = loginAtualizacao;
	}

	public Date getAtualizacao() {
		return atualizacao;
	}

	public void setAtualizacao(Date atualizacao) {
		this.atualizacao = atualizacao;
	}

	public Login getLoginRemocao() {
		return loginRemocao;
	}

	public void setLoginRemocao(Login loginRemocao) {
		this.loginRemocao = loginRemocao;
	}

	public Date getRemocao() {
		return remocao;
	}

	public void setRemocao(Date remocao) {
		this.remocao = remocao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
   
}
