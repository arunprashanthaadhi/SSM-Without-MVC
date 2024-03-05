import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class EditProfileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");

	    if (username != null && !username.isEmpty()) {
	        try {
	            String dbURL = "jdbc:mysql://localhost:3306/final";
	            String user = "root";
	            String pass = "";

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(dbURL, user, pass);

	            String sql = "SELECT * FROM user WHERE username = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, username);

	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                request.setAttribute("username", username);
	                request.setAttribute("firstname", rs.getString("firstname"));
	                request.setAttribute("lastname", rs.getString("lastname"));
	                request.setAttribute("dob", rs.getString("dob"));
	                request.setAttribute("email", rs.getString("email"));
	                request.setAttribute("address", rs.getString("address"));
	                request.setAttribute("about", rs.getString("about"));
	                RequestDispatcher dispatcher = request.getRequestDispatcher("editprofile.jsp");
	                dispatcher.forward(request, response);
	            }

	            rs.close();
	            stmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String about = request.getParameter("about");

        try {
            String dbURL = "jdbc:mysql://localhost:3306/final";
            String user = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, user, pass);

            String sql = "UPDATE user SET firstname = ?, lastname = ?, dob = ?, email = ?, address = ?, about = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, dob);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, about);
            stmt.setString(7, username);

            
            System.out.println("Executing SQL query: " + stmt.toString());
            int result = stmt.executeUpdate();
            if (result > 0) {
                response.sendRedirect("adminlanding.jsp");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Error Updating Profile");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
