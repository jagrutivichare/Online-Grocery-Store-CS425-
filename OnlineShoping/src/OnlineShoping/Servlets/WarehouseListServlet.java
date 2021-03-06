package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.Warehouse;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/warehouseList" })
public class WarehouseListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WarehouseListServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		String errorString = null;

		// Check User has logged on
		StaffMember loginedUser = MyUtils.getLoginedForStaffUser(session);
		// Not logged in
		if (loginedUser == null) {
			// Redirect to login page.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		List<Warehouse> list = null;

		try {
			list = DBUtils.queryWarehouse(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		request.setAttribute("errorString", errorString);
		request.setAttribute("warehouseList", list);

		// Forward to /WEB-INF/views/productListView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/warehouseList.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
