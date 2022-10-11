ALTER TABLE receita
ADD user_id INTEGER,
ADD CONSTRAINT fk_receitas_usuario
FOREIGN KEY (user_id) REFERENCES usuario(id);

ALTER TABLE despesa
ADD user_id INTEGER,
ADD CONSTRAINT fk_despesa_usuario
FOREIGN KEY (user_id) REFERENCES usuario(id);