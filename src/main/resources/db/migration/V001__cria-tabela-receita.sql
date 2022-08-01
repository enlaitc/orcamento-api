create table receita(

	id_receita serial primary key NOT NULL,
	descricao varchar(30) NOT NULL,
	valor float(10) NOT NULL,
	data date NOT NULL
);