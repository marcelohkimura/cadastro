package br.com.keepsimple.cadastro.model;

public enum Status {

    A("Ativo"),
    R("Removido");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
