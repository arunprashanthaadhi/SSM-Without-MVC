import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TimeTableStaffServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/final";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TimetableEntry> timetableEntries = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM timetable")) {

            while (resultSet.next()) {
                int timetableId = resultSet.getInt("timetableid");
                String day = resultSet.getString("day");
                String period1 = resultSet.getString("period1");
                String period2 = resultSet.getString("period2");
                String period3 = resultSet.getString("period3");
                String period4 = resultSet.getString("period4");
                String period5 = resultSet.getString("period5");
                String period6 = resultSet.getString("period6");
                String period7 = resultSet.getString("period7");

                TimetableEntry entry = new TimetableEntry(timetableId, day, period1, period2, period3, period4, period5, period6, period7);
                timetableEntries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("timetableEntries", timetableEntries);
        request.getRequestDispatcher("timetablestaff.jsp").forward(request, response);
    }
}