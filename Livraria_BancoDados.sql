CREATE DATABASE livraria
GO

USE livraria

CREATE TABLE editora (
	cod INT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	site VARCHAR(40) NULL
	PRIMARY KEY (cod)
)

CREATE TABLE autor (
	cod INT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	biografia VARCHAR(100) NOT NULL
	PRIMARY KEY (cod)
)

CREATE TABLE livro (
	cod  INT NOT NULL,
	titulo VARCHAR(100) NOT NULL,
	valor DECIMAL(7,2) NOT NULL,
	codEditora INT NOT NULL,
	codAutor INT NOT NULL
	PRIMARY KEY (cod)
	FOREIGN KEY (codEditora) REFERENCES editora (cod),
	FOREIGN KEY (codAutor) REFERENCES autor (cod)
)

CREATE TABLE estoque(
	cod INT NOT NULL,
	codLivro INT NOT NULL,
	quantidade INT NOT NULL
	PRIMARY KEY (cod)
	FOREIGN KEY (codLivro) REFERENCES livro (cod)
)

CREATE TABLE compra (
	cod INT NOT NULL,
	codLivro INT NOT NULL,
	qtdComprada INT NOT NULL,
	valor DECIMAL(7,2) NOT NULL,
	PRIMARY KEY (cod, codLivro),
	FOREIGN KEY (codLivro) REFERENCES livro (cod),
)

insert into autor values 
(4, 'Lucas', 'menos feio'),
(3, 'Uelen', 'feia'),
(2, 'William', 'meio feio'),
(1, 'felippe', 'feio')

insert into editora values
(3, 'Naruaito', 'Sasuke.com'),
(1, 'felippe', 'ofilhofeionaotempai.com'),
(2, 'Scrolar', 'Scrola.com')

insert into livro values
(1,'Amor e amor', 20.00,1,1),
(2,'Scrolando com sucesso', 50.00,2,1),
(3,'Naruitando com sucesso', 34.00,3,4),
(4,'Sucesso com sucesso', 99.00,1,3)

insert into estoque values
(1, 1, 25)

insert into compra values
(1, 1, 3, 20.00),
(1, 3, 6, 20.00),
(2, 2, 7, 20.00),
(3, 4, 5, 20.00)

select * from autor
select * from editora 
select * from livro
select * from estoque
select * from compra

--testes
select sum(qtdComprada) as valorsum from compra
select max(qtdComprada) as valormax from compra
select count(qtdComprada) as valormin from compra
select min(qtdComprada) as valoravg from compra
select avg(len(nome)) as nome from autor

-- 01) Selecione o codigo, titulo e o nome dos autores que tenham nos titulos do livro a palavra 'amor'

SELECT l.cod, l.titulo, a.nome
FROM livro l INNER JOIN autor a ON l.codAutor = a.cod
WHERE l.titulo like 'amor%'

--02) Selecione o codigo e o titulo dos livros que custam mais de 15,00 reais

SELECT l.cod, l.titulo
FROM livro l
WHERE l.valor > 15.00

--03) Selecione os nomes dos autores que tenha a primeira letra do nome 'A'

SELECT a.nome
FROM autor a
WHERE a.nome like 'F%'

--04)  Dê um desconto de 5% para todos os livros da editora 'Scrolar'

SELECT l.titulo,l.codEditora, l.valor ,
	CONVERT(decimal(7,2), (l.valor - (l.valor * 0.05))) AS valorDesconto
FROM livro l INNER JOIN editora e ON l.codEditora = e.cod
WHERE e.nome = 'Scrolar'

--05)  Soma de todos os valores dos livros

SELECT l.cod, SUM(l.valor) AS soma
FROM livro l
GROUP BY l.cod

--06) Organize a tabela com os valores dos livros em ordem decrescente 

SELECT l.titulo,MAX(l.valor) Valor
FROM livro l
GROUP BY l.titulo
ORDER BY l.titulo DESC