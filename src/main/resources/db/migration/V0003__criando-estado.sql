create table estado (
	ID SERIAL PRIMARY KEY,
	nome varchar(80) not null
);


alter table cidade add column estado_id bigint not null;


alter table cidade add constraint fk_cidade_estado
foreign key (estado_id) references estado (id);

alter table cidade drop column nome_estado;

alter table cidade RENAME COLUMN nome_cidade TO nome;