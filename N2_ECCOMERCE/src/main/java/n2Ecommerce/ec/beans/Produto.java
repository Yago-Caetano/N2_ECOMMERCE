package n2Ecommerce.ec.beans;

public class Produto {
	
	private int id;
	private String nome;
	private Double preco;
	private String descricao;
	private byte[] foto;
	private int quantidade;
	private Double desconto;
	private int quantidadeEmOrdem;
	private int idCategoria;

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if (id == null) {
			setId(0);
		} else {
		 setId(Integer.valueOf(id));
		}
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getPreco() {
		return this.preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public void setPreco(String preco) {
		if (preco == null) {
			setPreco(0.0);
		} else {
			setPreco(Double.valueOf(preco));
		} 
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public byte[] getFoto() {
		return this.foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public void setFoto(String foto) {
		if (foto == null) {
			this.foto = null;
		} else {
			this.foto = foto.getBytes();
		}
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public void setQuantidade(String quantidade) {
		if (quantidade == null) {
			setQuantidade(0);
		} else {
			setQuantidade(Integer.valueOf(quantidade));
		}
	}
	
	public Double getDesconto() {
		return this.desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public void setDesconto(String desconto) {
		if (desconto == null) {
			setDesconto(0.0);
		} else {
			setDesconto(Double.valueOf(desconto));
		} 
	}
	
	public int getQuantidadeEmOrdem() {
		return this.quantidadeEmOrdem;
	}
	public void setQuantidadeEmOrdem(int quantidadeEmOrdem) {
		this.quantidadeEmOrdem = quantidadeEmOrdem;
	}
	public void setQuantidadeEmOrdem(String quantidadeEmOrdem) {
		if (quantidadeEmOrdem == null) {
			setQuantidadeEmOrdem(0);
		} else {
			setQuantidadeEmOrdem(Integer.valueOf(quantidadeEmOrdem));
		}
	}
	
	public int getIdCategoria() {
		return this.idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		if (idCategoria == null) {
			setIdCategoria(0);
		} else {
			setIdCategoria(Integer.valueOf(idCategoria));
		}
	}
}
