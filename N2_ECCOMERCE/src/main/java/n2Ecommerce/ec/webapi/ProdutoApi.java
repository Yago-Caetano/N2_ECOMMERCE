package n2Ecommerce.ec.webapi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import n2Ecommerce.ec.beans.Produto;
import n2Ecommerce.ec.model.ProdutoDao;

/**
 * Servlet implementation class Api
 */
public class ProdutoApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProdutoApi() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Produto get - " + new Date());

		// TODO Auto-generated method stub
		Produto p = new Produto();
		
		// Note que getParamtere retorna string, 
		// a conversão é feita na classe Client (seters)
		
		p.setId(request.getParameter("ID"));
		p.setNome(request.getParameter("NOME"));
		p.setPreco(request.getParameter("PRECO"));
		p.setDescricao(request.getParameter("DESCRICAO"));
		p.setFoto(request.getParameter("FOTO"));
		p.setQuantidade(request.getParameter("QUANTIDADE"));
		p.setDesconto(request.getParameter("DESCONTO"));
		p.setQuantidadeEmOrdem(request.getParameter("QUANTIDADEEMORDEM"));
		p.setIdCategoria(request.getParameter("IDCATEGORIA"));
		
		ProdutoDao produtoDao = new ProdutoDao();
		
		produtoDao.insert(p);

        //TODO: Gerenciar e propagar erro...		
		
		System.out.println(request.getContentLength());
		
		if (request.getContentLength() < 1) {
			response.getWriter().append("{\"count\":" + produtoDao.count() + "}");
		} else {
			response.getWriter().append("Produto Criado...");
		} //if
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.getWriter().append("Served Post at: ").append(request.getContextPath()).append(" - ").append("oi");
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served Put at: ").append(request.getContextPath()).append(" - ").append("oi");
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub]
		response.getWriter().append("Served Delete at: ").append(request.getContextPath()).append(" - ").append("oi");
	}
}
