CREATE TABLE usuario(

	id serial,
	nome varchar(255),
	senha varchar(255),
	email varchar(255),

	primary key (id)

);

create table perfil(

	id serial,
	nome varchar(255),

	primary key(id)

);

create table usuario_perfis(

	usuario_id INTEGER,
	perfil_id INTEGER,

	primary key(usuario_id, perfil_id),

	foreign key(usuario_id) references usuario (id),
	foreign key(perfil_id) references perfil (id)

)
;