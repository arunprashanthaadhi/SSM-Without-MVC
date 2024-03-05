import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;

public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/final";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String role = validateUser(username, password);

        if (role != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);

            String redirectUrl = "";
            switch (role) {
                case "Admin":
                    redirectUrl = "adminlanding";
                    break;
                case "Staff":
                    redirectUrl = "StaffLandingServlet";
                    break;
                case "Student":
                    redirectUrl = "StudentLanding";
                    break;
                default:
                    redirectUrl = "login.html?error=true";
                    break;
            }
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("login.html?error=true");
        }
    }

    private String validateUser(String username, String password) {
        String userRole = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT u.username, ur.userrolename FROM user u INNER JOIN userrole ur ON u.userroleid = ur.userroleid WHERE u.username = ? AND u.password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userRole = rs.getString("userrolename");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRole;
    }
}
