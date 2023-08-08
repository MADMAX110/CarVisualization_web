package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charest=utf-8");
		String name = request.getParameter("username");
		String memo = request.getParameter("password");
		RequestDispatcher rd = null;
		if (name==null||name.equals("")) {
			rd = request.getRequestDispatcher("/index.jsp");
		}else {
			UserDAO userDao = new UserDAO();
			if (userDao.queryUser(name) != null && userDao.queryUser(name).equals(memo)) {
				request.getSession().setAttribute("loggedIn", true);
				rd = request.getRequestDispatcher("/home.jsp");
			}
			else {
				request.setAttribute("error", "用户名或密码错误");
				rd = request.getRequestDispatcher("/index.jsp");
			}
		}
		rd.forward(request,response);
	}

}
