package it.uniroma3.servlet;

import it.uniroma3.model.Product;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/product")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String price = request.getParameter("price");
		String description = request.getParameter("description");
		boolean errors = false;
		
		String nextPage = "/product.jsp";
		if ( name == null || name.equals("")) {
				request.setAttribute("nameErr","Name is mandatory");
				errors = true;
		}
		
		if ( code == null || code.equals("")) {
			request.setAttribute("codeErr","Code is mandatory");
			errors = true;
		}

		if ( price == null || price.equals("")) {
			request.setAttribute("priceErr","Price is mandatory");
			errors = true;
		}
		else {
			try{
				Float.parseFloat(price);
			}
			catch (NumberFormatException e) {
				request.setAttribute("priceErr","Price is must be a number");
				errors = true;
			}
		}

		if (!errors) {
			Product product = new Product();
			product.setName(name);
			product.setCode(code);
			product.setPrice(Float.valueOf(price));
			product.setDescription(description);
			HttpSession session = request.getSession();
			session.setAttribute("product", product);
		}
		else {
			nextPage = "/newProduct.jsp";
		}

		nextPage = response.encodeURL(nextPage);
		ServletContext application  = getServletContext();
		RequestDispatcher rd = application.getRequestDispatcher(nextPage);
		rd.forward(request, response);
		return; 
	}
}
