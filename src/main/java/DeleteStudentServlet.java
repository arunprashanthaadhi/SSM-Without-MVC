import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DeleteStudentServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/final";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdParam = request.getParameter("userid");
        if (studentIdParam == null || studentIdParam.isEmpty()) {
            response.getWriter().println("Student ID is missing or empty");
            return;
        }

        int studentId;
        try {
            studentId = Integer.parseInt(studentIdParam);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid student ID: " + studentIdParam);
            return;
        }
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "DELETE FROM studentmarksmapping WHERE studentid = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, studentId);

                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        response.sendRedirect("StudentListServlet");
                    } else {
                        response.getWriter().println("Student not found with ID: " + studentId);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database connection problem.", e);
        }
    }
}
