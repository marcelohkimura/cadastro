package br.com.keepsimple.cadastro.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column( nullable = false )
    private Integer numero;
    private String complemento;
    
    @ManyToOne
    @JoinColumn(name="id_cep")
    private Cep cep;

    public Endereco() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Cep getCep() {
		return cep;
	}

	public void setCep(Cep cep) {
		this.cep = cep;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cep, complemento, id, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(cep, other.cep) && Objects.equals(complemento, other.complemento)
				&& Objects.equals(id, other.id) && Objects.equals(numero, other.numero);
	}
    
}
