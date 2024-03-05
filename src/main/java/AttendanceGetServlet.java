import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AttendanceGetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/final";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT sa.*, CONCAT(u.firstname, u.lastname) AS studentName FROM studentattendance sa JOIN user u ON sa.studentid = u.userid"); // Adjust SQL as needed
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                AttendanceRecord record = new AttendanceRecord();
                record.setStudentAttendanceId(resultSet.getInt("studentAttendanceId"));
                record.setStudentId(resultSet.getInt("studentId"));
                record.setStudentName(resultSet.getString("studentName"));
                record.setPeriod1(resultSet.getBoolean("period1"));
                record.setPeriod2(resultSet.getBoolean("period2"));
                record.setPeriod3(resultSet.getBoolean("period3"));
                record.setPeriod4(resultSet.getBoolean("period4"));
                record.setPeriod5(resultSet.getBoolean("period5"));
                record.setAttendanceDate(resultSet.getDate("attendanceDate"));
                attendanceRecords.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("attendanceRecords", attendanceRecords);
        System.out.println("attendanceRecords------------->>>>>>>>>>"+attendanceRecords);
        request.getRequestDispatcher("/attendancesuccess.jsp").forward(request, response);
    }
}