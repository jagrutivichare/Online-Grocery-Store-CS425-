package OnlineShoping.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import OnlineShoping.Beans.Address;
import OnlineShoping.Beans.StaffMember;
import OnlineShoping.Beans.Warehouse;
import OnlineShoping.Utils.DBUtils;
import OnlineShoping.Utils.MyUtils;

@WebServlet(urlPatterns = { "/doAddWareHouse" })
public class DoAddWarehouse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DoAddWarehouse() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		// Check User has logged on
		StaffMember loginedUser = MyUtils.getLoginedForStaffUser(session);
		// Not logged in
		if (loginedUser == null) {
			// Redirect to login page.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		Connection conn = MyUtils.getStoredConnection(request);
		String warehouseName = (String) request.getParameter("warehouseName");
		String c = (String) request.getParameter("capacity");
		String BuildingNo = (String) request.getParameter("bn");
		String Street = (String) request.getParameter("street");
		String City = (String) request.getParameter("city");
		String State = (String) request.getParameter("state");
		String Zip = (String) request.getParameter("zip");

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String AddressID = warehouseName + "WH" + timestamp.getTime();

		Address address = new Address();
		address.setAddressID(AddressID);
		address.setBuildingNum(BuildingNo);
		address.setCity(City);
		address.setState(State);
		address.setStreet(Street);
		address.setZip(Zip);

		float capacity = 0;
		try {
			capacity = Float.parseFloat(c);
		} catch (Exception e) {
		}

		Warehouse warehouse = new Warehouse();
		warehouse.setAddress(address);
		warehouse.setWarehouseName(warehouseName);
		warehouse.setCapacity(capacity);

		String errorString = null;

		if (errorString == null) {
			try {
				DBUtils.AddWareHouse(conn, warehouse);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("warehouse", warehouse);

		// If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/addWarehouse.jsp");
			dispatcher.forward(request, response);
		}

		// If everything nice.
		// Redirect to the product listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/warehouseList");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
