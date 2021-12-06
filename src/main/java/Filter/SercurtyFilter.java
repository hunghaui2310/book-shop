package Filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;

import javax.servlet.http.*;

public class SercurtyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();
		// Kiểm tra các request trả về có chưa mấy cái đường link 
		boolean isStaticResource = request.getRequestURI().startsWith("/viewroot/");
		boolean isAdmin = request.getRequestURI().startsWith("/admin/viewroot/");
		HttpServletRequest wrapRequest = request;
//Kiêm tra các trạng trai lôi khi request 
		String url = request.getRequestURI();
		if (!ErrorServlet(request, response, response.getStatus())) {
			response.sendRedirect("login.jsp");
			filterChain.doFilter(wrapRequest, response);
			return;
		}
		if (url.indexOf("/viewroot") > 0 || url.indexOf("/admin/viewroot/") > 0) {
			filterChain.doFilter(request, response);// doFillter cho phép thông qua 
		}
		
// chưa các link cửa các role 
		String[] listpath = { "/Home", "/HomeProductsServlet", "/blog.jsp", "/contact.jsp", "/login.jsp",
				"/UsersLoginServlet", "/shoping-cart.jsp" ,"/HomeSearchProduct","/UsersRegisterServlet","/HomeProductsDetail"};
		String[] listpathUser = {"/CartControllerServlet", "/HomeUserProfileServlet", "/UserEditAvata", "/HomeProfileServlet","/account.jsp",
				"/checkout.jsp","/HomeCheckoutServlet", "/HomeBillServlet","/UsersLogoutServlet","/HomeCartServlet","/HomeShoppingCart"};
		String[] listPathAdmin = { "/AdminController","/AdminAddCategory","/AdminAddDetailProduct","/AdminBillsController","/AdminChangeRoleController","/AdminController","/AdminEditBookController",
				"/AdminDeleteCategory","/AdminDeleteBookDetail","/AdminDeleteProducts","/AdminDeleteUser","/AdminEditBookController","/AdminManagaBillController"
				,"/AdminManagaCategoryController","/AdminManagaBookController","/AdminManagaTransportController","/AdminManagaUserController","/AdminManageOrderController","/AdminMangageAuthorController",
				"/AdminOrderController","/AdminBookDetailsController","/AdminProfileController","/AdminTransportController","/UsersLogoutServlet","/AdminOrderCancelController","/AdminAuthorController"};
		for (String path : listpath) {
			if (servletPath.equals(path)) {
				filterChain.doFilter(request, response);
				break;
			}
		}
		//Kiêm tra và chăn và cho phép các user 
		Users loginedUser = (Users) session.getAttribute("uslogin");
		if (loginedUser != null) {
			if (loginedUser.getRole().equals("USER")) {
				for (String pathUser : listpathUser) {
					if (servletPath.equals(pathUser)) {
						filterChain.doFilter(request, response);
						break;
					}

				}
			} else if (loginedUser.getRole().equals("ADMIN")) {
				for (String pathAdmin : listPathAdmin) {
					if (servletPath.equals(pathAdmin)) {
						filterChain.doFilter(request, response);
						break;

					}
				}
			} else {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect("/login.jsp");
			}
		} else {
		}

	}
// kiếm tra các trạng trai lỗi của server
	public boolean ErrorServlet(HttpServletRequest request, HttpServletResponse response, int statusCode) {
		HttpSession session = request.getSession();
		if (statusCode > 0) {
			String error = "";
			String codeError = "";
			if (statusCode == 404) {
				error = "Lá»—i 404 Not Found lÃ   báº¡n cá»‘ gáº¯ng truy cáº­p vÃ o má»™t trang web (Ä‘Æ°á»�ng link ) Ä‘Ã£ khÃ´ng cÃ²n tá»“n táº¡i.";
				session.setAttribute("msgerror", error);
				codeError = "404";
				session.setAttribute("codeError", codeError);
				return false;
			} else if (statusCode == 500) {
				error = "Lá»—i 500 Internal Server Error:Táº¡m thá»�i khÃ´ng truy cáº­p Ä‘Æ°á»£c trang web";
				session.setAttribute("msgerror", error);
				codeError = "500";

				session.setAttribute("codeError", codeError);
				return false;
			} else if (statusCode == 405) {
				error = "HTTP Error 405 Method Not Allowed:Request method khÃ´ng Ä‘Æ°á»£c há»— trá»£ cho cÃ¡c tÃ i nguyÃªn Ä‘Æ°á»£c yÃªu cáº§u";
				session.setAttribute("msgerror", error);
				codeError = "405 ";
				session.setAttribute("codeError", codeError);
				return false;
			} else if (statusCode == 415) {
				error = "HTTP 415 Unsupported Media Type:Server sáº½ khÃ´ng cháº¥p nháº­n Request, bá»Ÿi vÃ¬ kiá»ƒu phÆ°Æ¡ng tiá»‡n khÃ´ng Ä‘Æ°á»£c há»— trá»£.";
				session.setAttribute("msgerror", error);
				codeError = "415 ";
				session.setAttribute("codeError", codeError);
				return false;

			}

		}
		return true;
	}

	@Override
	public void destroy() {

	}
}
