package sudha6;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FetchData
 */
@WebServlet("/FetchData")
public class FetchData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String dbUrl = "jdbc:mysql://localhost:3306/contactdb";
	private String dbUname = "root";
	private String dbPass = "Sudha@1234";
	private String dbDriver = "com.mysql.jdbc.Driver";
	
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUname, dbPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return con;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		loadDriver(dbDriver);
		Connection con = getConnection();
		response.setContentType("text/html");
		String cmobile = request.getParameter("mobile");
		//String sql = "select * from contactdb.contacts where cmob =";
		try {
			PreparedStatement ps = con.prepareStatement("select * from contactdb.contacts where cmob ="+cmobile);
			ResultSet rs = ps.executeQuery();
			out.println("<html><body><center><table border='7'>"
					+"<h1>===The Selected Value Table===</h1>"
					+"<tr><td>NAME</td><td>MOBILE NUMBER</td><td> E_Mail</td></tr>");
			while(rs.next()) {
				out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td></tr>");
			}
			out.println("</table></center></body></html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("No data available");
		}
	}

}
