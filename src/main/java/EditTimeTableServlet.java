import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class EditTimeTableServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/final";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String day = request.getParameter("day");

        if (day != null && !day.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM timetable WHERE day = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, day);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    request.setAttribute("day", rs.getString("day"));
                    request.setAttribute("period1", rs.getString("period1"));
                    request.setAttribute("period2", rs.getString("period2"));
                    request.setAttribute("period3", rs.getString("period3"));
                    request.setAttribute("period4", rs.getString("period4"));
                    request.setAttribute("period5", rs.getString("period5"));
                    request.setAttribute("period6", rs.getString("period6"));
                    request.setAttribute("period7", rs.getString("period7"));

                    RequestDispatcher dispatcher = request.getRequestDispatcher("edittimetable.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("TimetableListServlet");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String day = request.getParameter("day");
        String period1 = request.getParameter("period1");
        String period2 = request.getParameter("period2");
        String period3 = request.getParameter("period3");
        String period4 = request.getParameter("period4");
        String period5 = request.getParameter("period5");
        String period6 = request.getParameter("period6");
        String period7 = request.getParameter("period7");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE timetable SET period1=?, period2=?, period3=?, period4=?, period5=?, period6=?, period7=? WHERE day=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, period1);
            stmt.setString(2, period2);
            stmt.setString(3, period3);
            stmt.setString(4, period4);
            stmt.setString(5, period5);
            stmt.setString(6, period6);
            stmt.setString(7, period7);
            stmt.setString(8, day);

            int result = stmt.executeUpdate();
            if (result > 0) {
                response.sendRedirect("ViewTimeTableServlet");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Error Updating Timetable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
