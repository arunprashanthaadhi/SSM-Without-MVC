import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentLanding extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/final";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId != null) {
                String sql = "SELECT u.userid, u.username, ur.userrolename FROM user u INNER JOIN userrole ur ON u.userroleid = ur.userroleid WHERE u.userid = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            String username = resultSet.getString("username");
                            String role = resultSet.getString("userrolename");
                            request.setAttribute("username", username);
                            request.setAttribute("role", role);
                            
                            System.out.println("Username retrieved from database: " + username);
                            System.out.println("Role retrieved from database: " + role);
                        } else {
                            System.out.println("No data found for user ID: " + userId);
                        }
                    }
                }
            } else {
                System.out.println("User ID is null");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database error", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/studentlanding.jsp");
        dispatcher.forward(request, response);
    }
}
