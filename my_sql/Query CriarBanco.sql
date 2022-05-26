Create database N2_ecommerce;

Use N2_ecommerce;

create table tbTipoUsuario(
	id varchar(40) not null,
	Tipo varchar(20) not null unique,
	primary key(id)
);

create table tbUsuario(
	id varchar(40) not null,
	Nome varchar(20) not null,
	email varchar(30) unique not null,
	senha varchar(80) not null,
	cpf varchar(20) not null unique,
	idTipoUsuario varchar(40) not null,
    foreign key(idTipoUsuario) references tbTipoUsuario(id),
	statusUsuario BIT default 1, -- indica se o usuario est� ativo ou n�o
	primary key(id)
);

/**
	Indice Criado para agilizar o processo de login
**/
 CREATE INDEX IndicetbUsuario_email
 ON tbUsuario (email);
 
 
create table tbEnderecos(
	id varchar(40) not null,
	Rua varchar(50) not null,
	Complemento varchar(20) not null,
	numero int not null,
	Cep varchar(10) not null,
	Cidade varchar(50) not null,
	statusEnd BIT default 1, -- indica se o endere�o est� ativo ou n�o
	primary key (id)
);

create table tbUsuarioxEnderecos(
	id_usuario varchar(40) ,
	id_endereco varchar(40) ,
    foreign key(id_usuario) references tbUsuario (id),
    foreign key (id_endereco) references tbEnderecos (id),
	primary key (id_usuario,id_endereco)
);

create table tbCategoriaProdutos(
	id varchar(40) not null,
	Categoria varchar(20) not null unique,
	primary key(id)
);

create table tbProdutos(
	id varchar(40) not null,
	Nome varchar(20) not null unique,
	Preco decimal(15,2) not null,
	Descricao varchar(100) not null,
	Foto LongBLOB,
	Quantidade int not null default 0,
	Desconto real default 0.00,
	idCategoria varchar(40),
    foreign key(idCategoria) references tbCategoriaProdutos (id),
    statusEnd BIT default 1,
	primary key (id)
);

/**
	Indice criado para agilizar a pesquisa por nome do produto
**/
 CREATE INDEX IndicetbProdutos_Nome
 ON tbProdutos (Nome);
 
create table tbStatusPedido(
	id varchar(40) not null,
	PedidoStatus varchar(20) not null unique,
	primary key(id)
);

create table tbPedidos(
	id varchar(40) not null,
    Data_pedido timestamp DEFAULT CURRENT_TIMESTAMP,
	idStatus varchar(40) not null,
	idUsuario varchar(36) not null,
	idEndereco varchar(36) not null,
    foreign key(idStatus) references tbStatusPedido (id),
	foreign key (idUsuario,idEndereco) references tbUsuarioxEnderecos(id_usuario,id_endereco),
	primary key (id)
);

create table tbPedidosxProdutos(
	idPedido varchar(40) not null,
	idProduto varchar(40) not null,
	Quantidade int not null default 1,
	Desconto real default 0.00 not null,
	Preco decimal(15,2) not null,
	foreign key(idPedido) references tbPedidos (id),
    foreign key(idProduto) references tbProdutos (id),
	primary key (idPedido,idProduto)
);


insert into tbTipoUsuario (id,Tipo) Values ('unjawhe767','Gerente');
insert into tbTipoUsuario (id,Tipo) Values ('aiwbduaoia','Usuario');

insert into tbStatusPedido (id,PedidoStatus) values ('aoiuhwda23','Pendente');
insert into tbStatusPedido (id,PedidoStatus) values ('aiwujhac1235','Concluído');
insert into tbStatusPedido (id,PedidoStatus) values ('auihdbayuwidh2131313','Cancelado');




insert into tbCategoriaProdutos (id,Categoria) values ('1','Barracas');
insert into tbCategoriaProdutos (id,Categoria) values ('2','Baioneta');
insert into tbCategoriaProdutos (id,Categoria) values ('3','Ferramentas');
insert into tbCategoriaProdutos (id,Categoria) values ('4','Cantil');
insert into tbCategoriaProdutos (id,Categoria) values ('5','Gandola');
insert into tbCategoriaProdutos (id,Categoria) values ('6','Sacos de dormir');
insert into tbCategoriaProdutos (id,Categoria) values ('7','Bornal');

insert into tbProdutos (id,Nome,Preco,Descricao,Foto,Quantidade,Desconto,idCategoria,statusEnd) 
Values ('1','Barraca 1',250.52,'Barraca 1 ','iVBORw0KGgoAAAANSUhEUgAAAJIAAACHCAYAAAD9YACmAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAILPSURBVHhe7b0FYBXX2jWcW8EhxN09wd2lVLHi7u4uxd29BUoFKFBKKbS4O4SEKEmQuLvL8ZzkrH/tSQ6luel9v972ve+992e3i5kzM2cys/fa61nPHjkGeFPelL+gvCHSm/K...'
	,100,5.0,'1',1);
													

						       
						       


