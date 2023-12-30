drop table if exists usuario;
drop table if exists endereco;
drop table if exists cep;
drop table if exists login;

create table login
(
    id INTEGER unsigned not null auto_increment,   
    email VARCHAR(128) not null,
    password VARCHAR(15) not null,
    PRIMARY KEY (ID)    
);
INSERT INTO login(email, password) VALUES ('marcelohkimura@gmail.com', '123456');

create table cep
(
  id INTEGER unsigned not null auto_increment,
  cep VARCHAR(8) not null,
  rua VARCHAR(128) not null,
  bairro VARCHAR(128) not null,
  cidade VARCHAR(128) not null,
  uf VARCHAR(2) not null,
  PRIMARY KEY (id),
  UNIQUE(cep)
);

INSERT INTO cep(cep, rua, bairro, cidade, uf) VALUES ('04216001', 'Rua Mil Oitocentos e Vinte e Dois', 'Ipiranga', 'São Paulo', 'SP');

create table endereco
(
	id INTEGER unsigned not null auto_increment,
	id_cep INTEGER UNSIGNED NOT null,
	numero INTEGER not null,
	complemento VARCHAR(128),
	PRIMARY KEY (id),
	FOREIGN KEY (id_cep) REFERENCES cep(id)
);

create table usuario
(   
    id INTEGER unsigned not null auto_increment,   
    cpf VARCHAR(15) not null,
    nome VARCHAR(128) not null,
    nascimento DATE not null,
    id_endereco INTEGER unsigned not null,
    status char(1) not null,
    id_login_criacao INTEGER unsigned not null,
    data_criacao DATE not null,
    id_login_atualizacao INTEGER unsigned,
    data_atualizacao DATE,    
    id_login_remocao INTEGER unsigned,
    data_remocao DATE,    
    PRIMARY KEY (id),
    UNIQUE(cpf),
	FOREIGN KEY (id_endereco) REFERENCES endereco(id),    
	FOREIGN KEY (id_login_criacao) REFERENCES login(id),
	FOREIGN KEY (id_login_atualizacao) REFERENCES login(id),
	FOREIGN KEY (id_login_remocao) REFERENCES login(id)
);



