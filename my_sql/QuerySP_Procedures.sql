Use N2_ecommerce;

DELIMITER $$
/*Tipos de usuario*/
create procedure spInsert_tbTipoUsuario(id varchar(36), Tipo varchar(20))  /*--as SP podem receber parametros*/
begin
	insert into tbTipoUsuario(Tipo) 
	values (Tipo);
end
$$

DELIMITER ;
DELIMITER $$
create procedure spUpdate_tbTipoUsuario(id varchar(36), Tipo varchar(20))
begin
	update tbTipoUsuario set
	Tipo=Tipo
	where id=id;
end
$$

DELIMITER ;
DELIMITER $$

/*-----------------------------------------------------------------------------------Categoria de produtos*/
create procedure spInsert_tbCategoriaProdutos(id varchar(36), Categoria varchar(20)) /* --as SP podem receber parametros*/
begin
	insert into tbCategoriaProdutos(Categoria) 
	values (Categoria);
end
$$

DELIMITER ;
DELIMITER $$
create procedure spUpdate_tbCategoriaProdutos(id varchar(36), Categoria varchar(20))
begin
	update tbCategoriaProdutos set
	Categoria=Categoria
	where id=id;
end
$$

DELIMITER ;
DELIMITER $$

create procedure spFiltro_tbProdutos
(	
	Nome varchar(20) ,
	idCategoria varchar(36),
	ordem varchar(16383),
	PrecoInicial DECIMAL(15,2),
	PrecoFinal DECIMAL(15,2)
)  
	begin
		declare sqlI varchar(16383);
		set sqlI =
		'select * from tbProdutos '+
		'where tbProdutos.Nome like ''%' + Nome + '%''';
		if idCategoria != '' then
			begin
				set sqlI = sqlI + ' and idCategoria=' + idCategoria;
			end;
		end if;
		if PrecoInicial is not null and PrecoFinal is not null then
			begin 
				-- print 'PrecoInicial is not null and PrecoFinal is not null '
				set sqlI = sqlI + ' and tbProdutos.Preco between '+
					cast(PrecoInicial as decimal(15,2)) + 
					' and '+
					cast(PrecoFinal as decimal(15,2));
			end;
		end if;
		if PrecoInicial is null and PrecoFinal is not null then
			begin
				set sqlI = sqlI + ' and tbProdutos.Preco <= ' +cast(PrecoFinal as decimal(15,2));
			end;
		end if;
		if PrecoFinal is null and PrecoInicial is not null then
			begin
				set sqlI = sqlI + ' and tbProdutos.Preco >= ' +cast(PrecoInicial as decimal(15,2));
			end;
            end if;
		If ordem<>'' then
			begin
				set sqlI = sqlI + ' order by ' + ordem;
			end;
		end if;
        
		-- print sqlI;
		execute sqlI;
		
	end;
$$
DELIMITER ;
DELIMITER $$

create procedure spInsert_tbProdutos
(	id varchar(36), 
	Nome varchar(20),
	Preco DECIMAL(15,2) ,
	Descricao varchar(100),
	Foto BLOB,
	Quantidade int,
	Desconto real,
	idCategoria varchar(36)
) /* --as SP podem receber parametros*/
begin
	insert into tbProdutos(Nome,Preco,Descricao,Foto,Quantidade,Desconto,idCategoria) 
	values (Nome,Preco,Descricao,Foto,Quantidade,Desconto,idCategoria);
end
$$

DELIMITER ;
DELIMITER $$
create procedure spUpdate_tbProdutos
(	id varchar(36), 
	Nome varchar(20),
	Preco DECIMAL(15,2) ,
	Descricao varchar(100),
	Foto BLOB,
	Quantidade int,
	Desconto real,
	idCategoria varchar(36)
) /* --as SP podem receber parametros*/
begin
	update tbProdutos set
	Nome =Nome,
	Preco =Preco ,
	Descricao =Descricao,
	Foto =Foto,
	Quantidade=Quantidade,
	Desconto=Desconto,
	idCategoria=idCategoria
	where id =id;

end
$$

DELIMITER ;
DELIMITER $$

create procedure spConsultaNomeProduto (Id varchar(36))
begin 
	select id,Nome from tbProdutos where id=Id;
end
$$

DELIMITER ;
DELIMITER $$

create procedure spFiltro_tbUsuario
(	 
	Nome varchar(20),
	ordem varchar(200),
	dataInicial date,
	dataFinal date
)  /*--as SP podem receber parametros*/
begin
	declare sqli varchar(16383);
	set sqli = 
	   'set dateformat ymd; '+
	   'select * from tbUsuario '+
	   'where tbUsuario.Nome like ''%' + Nome + '%'' and ' +
	  '      tbUsuario.Nascimento between ' +
	  CONCAT("'",convert(dataInicial, CHAR), "'") + 
	  ' and ' + 
	  CONCAT("'",convert(dataFinal, CHAR), "'");

	  If ordem<>'' then
		set sqli = sqli + ' order by ' + ordem;
		END IF;
	-- print sqli;
	EXECUTE sqli;
	
end
$$

DELIMITER ;
DELIMITER $$

create procedure spInsert_tbUsuario
(	id varchar(36), 
	Nome varchar(20),
	Nascimento datetime ,
	email varchar(30),
	senha  varchar(30),
	cpf  varchar(20),
	idTipoUsuario  varchar(36),
	statusUsuario bit
)  /*--as SP podem receber parametros*/
begin
	declare nascAux datetime;
    set nascAux = date_format(Nascimento,'%d-%m-%y');
	insert into tbUsuario(Nome,nascAux,email,senha,cpf,idTipoUsuario) 
	values (Nome,Nascimento,email,senha,cpf,idTipoUsuario);
end
$$
DELIMITER ;
DELIMITER $$

create procedure spUpdate_tbUsuario
(	id varchar(36), 
	Nome varchar(20),
	Nascimento datetime ,
	email varchar(30),
	senha  varchar(30),
	cpf  varchar(20),
	idTipoUsuario  varchar(36),
	statusUsuario bit
)  /*--as SP podem receber parametros*/
begin
	update tbUsuario set
	Nome=Nome,
	Nascimento=date_format(Nascimento,"%d-%m-%y"),
	email=email,
	senha=senha,
	cpf=cpf,
	idTipoUsuario=idTipoUsuario
	where id =id;
end
$$

DELIMITER ;
DELIMITER $$
Create procedure spDelete_Usuario(idUsuario varchar(36)) /* -- Usuarios n�o s�o realmente deletados, s� desativados*/
begin
	declare idPedido varchar(36);
	declare idEndereco varchar(36);
	declare idPedidoItem varchar(36);
    declare finished int default 0;
	declare sFinished int default 0;
	declare tFinished int default 0;

	declare cursorEndereco cursor for select id_endereco from tbUsuarioxEnderecos where id_usuario = idUsuario;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
    
    
	start transaction;

	update tbUsuario set
	statusUsuario =0  -- para garantir os dados adequados do pedido mesmo que o usuario seja deletado
	where id =idUsuario;
	
	open cursorEndereco;
	fetch next from cursorEndereco into idEndereco;
	while finished = 0 do
		update tbEnderecos set
		statusEnd = 0  -- para garantir os dados adequados do pedido mesmo que o usuario seja deletado
		where id =idEndereco;
		fetch next from cursorEndereco into idEndereco;
	end while;
    
BLOCK2: BEGIN
	declare cursorPedido cursor for select id from tbPedidos where idUsuario = idUsuario and idStatus=1;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET sFinished = 1;
    
    	open cursorPedido;

	fetch next from cursorPedido into idPedido;

	while sFinished = 0  do
		begin
			-- print 'Pedido encontrado: ' + idPedido
			declare cursorPedidoItem cursor for select idProduto from tbPedidosxProdutos where idPedido = idPedido;
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET tFinished = 1;
			open cursorPedidoItem;
			fetch next from cursorPedidoItem into idPedidoItem;
			while  tFinished = 0 do
				begin
					call spUpdate_tbPedidosxProdutos(idPedido,idPedidoItem,0);
					fetch next from cursorPedidoItem into idPedidoItem;
				end;
			end while;
			close cursorPedidoItem;

			delete from tbPedidosxProdutos where idPedido=idPedido;
		
			delete from tbPedidos where id=idPedido;
			fetch next from cursorPedido into idPedido;
		end;
	end while;
	close cursorPedido;

END;
	close cursorEndereco;

	commit;

end;
$$

DELIMITER ;
DELIMITER $$
create procedure spVerificaUsuario(login varchar(30),senha  varchar(30))
begin 
	select * from tbUsuario where email=login and senha=senha and statusUsuario=1;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spConsultaDadosUsuario (idUsuario varchar(36))
begin
	select id,Nome,email,cpf from tbUsuario where id=idUsuario;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spConsultaEnderecosUsuario (idUsuario varchar(36)) 
begin
	select tbE.id from tbUsuarioxEnderecos tbUE inner join tbEnderecos tbE on tbUE.id_endereco=tbE.id
	where tbUE.id_usuario=idUsuario and tbE.statusEnd=1;
end

$$

DELIMITER ;
DELIMITER $$
-- ---------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------Enderecos
create procedure spFiltro_tbEnderecos
(	
	id varchar(36), 
	Rua varchar(20),
	Complemento varchar(20),
	numero int ,
	CEP varchar(10),
	Cidade varchar(15),
	statusEnd BIT,
	ordem varchar(16383)
)
  -- as SP podem receber parametros
begin
		declare sqli varchar(16383);
		set sqli = 
		'select * from tbEnderecos '+
		' where tbEnderecos.Rua like ''%' + Rua + '%'' and ' +
		' tbEnderecos.CEP like ''%' + CEP + '%'' and ' +
		' tbEnderecos.Cidade like ''%' + Cidade + '%''';

	
	  If ordem<>'' then
		set sqli = sqli + ' order by ' + ordem;
	 end if;
	-- print sqli;
	execute sqli;

end

$$

DELIMITER ;
DELIMITER $$
create procedure spInsert_tbEnderecos
(	id varchar(36), 
	Rua varchar(20),
	Complemento varchar(20),
	numero int ,
	CEP varchar(10),
	Cidade varchar(15),
	statusEnd BIT
)
  -- as SP podem receber parametros
begin
	insert into tbEnderecos(Rua,Complemento,numero,CEP,Cidade,statusEnd) 
	values (Rua,Complemento,numero,CEP,Cidade,statusEnd); 
end
$$

DELIMITER ;
DELIMITER $$
create procedure spUpdate_tbEnderecos
(	id varchar(36), 
	Rua varchar(20),
	Complemento varchar(20),
	numero int ,
	CEP varchar(10),
	Cidade varchar(15),
	statusEnd BIT
)
  -- as SP podem receber parametros
begin
	update tbEnderecos set
	Rua=Rua,
	Complemento=Complemento,
	numero=numero,
	CEP=CEP,
	Cidade=Cidade,
	statusEnd=statusEnd
	where id=id;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spDelete_Enderecos(idEndereco varchar(36))  -- Enderecos n�o s�o realmente deletados, s� desativados
begin
	update tbEnderecos set
	statusEnd =0  -- para garantir os dados adequados do pedido mesmo que o usuario seja deletado
	where id =idEndereco;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spInsertUserEndereco(idUsuario varchar(36),idEndereco varchar(36)) 
begin
	insert into tbUsuarioxEnderecos (id_usuario,id_endereco) Values
	(idUsuario,idEndereco);
end
$$

DELIMITER ;
DELIMITER $$
-- ------------------------------------------------------------------------------------------- Pedidos
create procedure spGetAllPedidos( idstatus int)  
 
	begin
		declare Quantidade int;
		declare Desconto real;
		declare Preco decimal(15,2);
		declare Total decimal(15,2);
		declare Auxiliar decimal(15,2);
		declare id varchar(36);
		declare Data_value datetime;
		declare Nome varchar(20);
		declare Cidade varchar(50);
		declare CEP varchar(10);

		create temporary table temporaria(
		id varchar(36),
		Data_Value datetime,
		Nome varchar(20),
		Cidade varchar(50),
		CEP varchar(10),
		Valor decimal(15,2)
		);
		if idstatus=1 then
			begin
				declare CursorPedidos cursor for select* from vw_Pedidos_Em_Aberto;
                
                Open CursorPedidos;
		fetch next from CursorPedidos into id,Data_Value,Nome,Cidade,CEP;

		while FETCH_STATUS = 0 
			do
			begin
				declare CursorItensPedido cursor for select Quantidade,Desconto,Preco from tbPedidosxProdutos where idPedido = id;
				open CursorItensPedido;
				set Total=0.00;
				fetch next from CursorItensPedido into Quantidade,Desconto,Preco;
				while FETCH_STATUS = 0 
					do
					begin
						set Auxiliar=Quantidade*Preco*(1-(Desconto/100));
						Set Total=Total+Auxiliar;
						
						fetch next from CursorItensPedido into Quantidade,Desconto,Preco;
					end;
                    end while;
				close CursorItensPedido;
                
				insert into temporaria (id,Data_Value,Nome,Cidade,CEP,Valor) Values(id,Data_value,Nome,Cidade,CEP,Total);
				fetch next from CursorPedidos into id,Data_value,Nome,Cidade,CEP;
			end;
			end while;
			close CursorPedidos;
			
			end;
		else
			begin
				declare CursorPedidos cursor for select* from vw_Pedidos_Concluidos;
                
                                Open CursorPedidos;
		fetch next from CursorPedidos into id,Data_Value,Nome,Cidade,CEP;

		while FETCH_STATUS = 0 
			do
			begin
				declare CursorItensPedido cursor for select Quantidade,Desconto,Preco from tbPedidosxProdutos where idPedido = id;
				open CursorItensPedido;
				set Total=0.00;
				fetch next from CursorItensPedido into Quantidade,Desconto,Preco;
				while FETCH_STATUS = 0 
					do
					begin
						set Auxiliar=Quantidade*Preco*(1-(Desconto/100));
						Set Total=Total+Auxiliar;
						
						fetch next from CursorItensPedido into Quantidade,Desconto,Preco;
					end;
                    end while;
				close CursorItensPedido;
                
				insert into temporaria (id,data,Nome,Cidade,CEP,Valor) Values(id,Data_value,Nome,Cidade,CEP,Total);
				fetch next from CursorPedidos into id,Data_value,Nome,Cidade,CEP;
			end;
			end while;
			close CursorPedidos;
			end;
		end if;
        
		
		select * from temporaria;
	end;

$$

DELIMITER ;
DELIMITER $$
create procedure spInsert_tbPedidos
(
	id varchar(36),
	idStatus int,
	idUsuario varchar(36),
	idEndereco varchar(36),
	data datetime
) 
begin
	insert into tbPedidos (idStatus,idUsuario,idEndereco,data) Values 
	(idStatus,idUsuario,idEndereco,data);

end
$$

DELIMITER ;
DELIMITER $$
create procedure spUpdate_tbPedidos
(
	id varchar(36),
	idStatus int,
	idUsuario varchar(36),
	idEndereco varchar(36),
	data datetime
) 
begin
	update tbPedidos set

	idStatus =idStatus,
	idUsuario=idUsuario,
	idEndereco=idEndereco,
	data=data
	where id=id;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spListaPedidosByCliente (idCliente int) 
	begin
		Select* from tbPedidos where idUsuario=idCliente;
	end
$$

DELIMITER ;
DELIMITER $$
-- -------------------------------------------------------------------------------------------Pedido produto
create procedure spInsert_tbPedidosxProdutos
(
	idPedido varchar(36) ,
	idProduto varchar(36) ,
	Quantidade int
)

begin
	insert into tbPedidosxProdutos (idPedido,idProduto,Quantidade) Values
	(idPedido,idProduto,Quantidade);
end
$$

DELIMITER ;
DELIMITER $$
create procedure spUpdate_tbPedidosxProdutos
(
	idPedido varchar(36) ,
	idProduto varchar(36) ,
	Quantidade int
)

begin
	update tbPedidosxProdutos set
	Quantidade= Quantidade
	where idPedido =idPedido and idProduto =idProduto;
end
$$

DELIMITER ;
DELIMITER $$

create procedure spDelete_tbPedidosxProdutos
(
	idPedido varchar(36) ,
	idProduto varchar(36)
)

begin
	call spUpdate_tbPedidosxProdutos(idPedido,idProduto,0);
	delete from tbPedidosxProdutos where idPedido = idPedido and idProduto = idProduto;
end
$$

DELIMITER ;
DELIMITER $$
create procedure splistar_itensPedido
(
	idPedido varchar(36)
)
 begin 
	select * from tbPedidosxProdutos where idPedido=idPedido;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spConsulta_tbPedidosxProdutos
(
	idPedido varchar(36) ,
	idProduto varchar(36)
)

begin
	select * from tbPedidosxProdutos where idPedido=idPedido and idProduto=idProduto;
end

$$

DELIMITER ;
DELIMITER $$
-- -------------------------------------------------------------------------------------------
-- --------------------------------------------------------------------------------------Procedures genericas
create procedure spDelete
(
 id varchar(36) ,
 tabela varchar(16383)
)

begin
 declare sqli varchar(16383);
 set sqli = ' delete ' + tabela +
 ' where id = ' + id;
 execute sqli;
end
$$

DELIMITER ;
DELIMITER $$

create procedure spConsulta
(
 id varchar(36) ,
 tabela varchar(16383)
)

begin
 declare sqli varchar(16383);
 set sqli = 'select * from ' + tabela +
 ' where id = ' + id;
 execute sqli;
end

$$

DELIMITER ;
DELIMITER $$
create procedure spListagem
(
 tabela varchar(16383),
 ordem varchar(16383))

begin
 declare sqli varchar(16383);
 set sqli =  ('select * from ' + tabela +
 ' order by ' + ordem);
 execute sqli;
end
$$

DELIMITER ;
DELIMITER $$
create procedure spProximoId
(tabela varchar(16383))

begin
 declare sqli varchar(16383);
 set sqli = ('select isnull(max(id) +1, 1) as MAIOR from '
 +tabela);
 execute sqli;
end
$$

-- ------------------------------------------------------------------------------------------------