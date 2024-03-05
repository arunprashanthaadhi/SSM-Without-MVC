import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceStaffServlet extends HttpServlet {
    private final String URL = "jdbc:mysql://localhost:3306/final";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "SELECT sa.*, CONCAT(u.firstname, u.lastname) AS username FROM studentattendance sa JOIN user u ON sa.studentid = u.userid WHERE sa.attendancedate = CURDATE()";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        AttendanceRecord record = new AttendanceRecord();
                        record.setStudentName(rs.getString("username"));
                        record.setStudentId(rs.getInt("studentid"));
                        record.setPeriod1(rs.getBoolean("period1"));
                        record.setPeriod2(rs.getBoolean("period2"));
                        record.setPeriod3(rs.getBoolean("period3"));
                        record.setPeriod4(rs.getBoolean("period4"));
                        record.setPeriod5(rs.getBoolean("period5"));
                        record.setAttendanceDate(rs.getDate("attendancedate"));
                        attendanceRecords.add(record);
						/*
						 * System.out.println("attendanceRecords--------------->>>>>>>>>>>>>" +
						 * attendanceRecords);
						 */
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("attendanceRecords", attendanceRecords);
        request.getRequestDispatcher("/viewattendancestaff.jsp").forward(request, response);
    }
}
