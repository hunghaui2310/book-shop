package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.BookBO;
import bo.CartBO;
import dao.CartDAO;
import model.Books;
import model.Cart;
import model.Item;
import model.Users;

/**
 * Servlet implementation class HomeCartServlet
 */
public class HomeCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeCartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		BookBO bo = new BookBO();
		CartBO cartBO = new CartBO();

		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("uslogin");
		String idr = request.getParameter("id_product");
		String status = request.getParameter("status");
		Cart cart = null;
		cart = cartBO.getCartByIDU(user.getId());

		if(cart==null) {
			Cart cartN = new Cart();
			cartN.setId_User(user.getId());
			cartBO.addCart(cartN);
			cart=cartBO.getCartByIDU(user.getId());
		}
		List<Item> listItem = null;
		long id = 0;
		if (status.equals("add")) {
			id = Long.parseLong(idr);
			boolean check = true;
			List<Item> items = cartBO.getListItem(cart.getId());
			if (items.size() > 0) {
				for (Item item : items) {
					if (item.getIdBook() == id) {
						int a = item.getQuantity() + 1;
						item.setQuantity(a);
						cartBO.updateItem(item);
						check = false;
						break;
					}

				}
			}
			if (check) {
				Item item = new Item();
				item.setIdBook(id);
				item.setQuantity(1);
				item.setCart(cart);
				cartBO.addItem(item);
			}
			int amount = cart.getAmount() + 1;
			Books books = bo.getBookByID(id);
			cart.setAmount(amount);
			double cartPrice = cart.getTotalPrice() + books.getPriceBook();
			cart.setTotalPrice(cartPrice);
			cartBO.updateCart(cart);
			Cart cartUp=cartBO.getCart(cart.getId());
			session.setAttribute("cart", cartUp);

		} else {
			session.setAttribute("cart", listItem.size());
			response.sendRedirect(request.getHeader("referer"));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
