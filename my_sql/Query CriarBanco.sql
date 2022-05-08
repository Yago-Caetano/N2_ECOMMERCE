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
	id varchar(36) not null,
	Rua varchar(50) not null,
	Complemento varchar(20) not null,
	numero int not null,
	Cep varchar(10) not null,
	Cidade varchar(50) not null,
	statusEnd BIT default 1, -- indica se o endere�o est� ativo ou n�o
	primary key (id)
);

create table tbUsuarioxEnderecos(
	id_usuario varchar(36),
	id_endereco varchar(36),
    foreign key(id_usuario) references tbUsuario (id),
    foreign key (id_endereco) references tbEnderecos (id),
	primary key (id_usuario,id_endereco)
);

create table tbCategoriaProdutos(
	id varchar(36) not null,
	Categoria varchar(20) not null unique,
	primary key(id)
);

create table tbProdutos(
	id varchar(36) not null,
	Nome varchar(20) not null unique,
	Preco decimal(15,2) not null,
	Descricao varchar(100) not null,
	Foto BLOB,
	Quantidade int not null default 0,
	Desconto real default 0.00,
	idCategoria varchar(36),
    foreign key(idCategoria) references tbCategoriaProdutos (id),
	primary key (id)
);

/**
	Indice criado para agilizar a pesquisa por nome do produto
**/
 CREATE INDEX IndicetbProdutos_Nome
 ON tbProdutos (Nome);
 
create table tbStatusPedido(
	id int not null,
	PedidoStatus varchar(20) not null unique,
	primary key(id)
);

create table tbPedidos(
	id varchar(36) not null,
	idStatus int not null,
	idUsuario varchar(36) not null,
	idEndereco varchar(36) not null,
    foreign key(idStatus) references tbStatusPedido (id),
	foreign key (idUsuario,idEndereco) references tbUsuarioxEnderecos(id_usuario,id_endereco),
	data datetime not null,
	primary key (id)
);

create table tbPedidosxProdutos(
	idPedido varchar(36) not null,
	idProduto varchar(36) not null,
	Quantidade int not null default 1,
	Desconto real default 0.00 not null,
	Preco decimal(15,2) not null,
	foreign key(idPedido) references tbPedidos (id),
    foreign key(idProduto) references tbProdutos (id),
	primary key (idPedido,idProduto)
);
