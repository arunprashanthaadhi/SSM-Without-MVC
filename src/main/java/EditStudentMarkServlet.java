import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditStudentMarkServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/final";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        List<Student> studentList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "SELECT u.userid, smm.rollnumber, u.firstname, u.lastname, smm.english, smm.cs, smm.maths, smm.physics, smm.chemistry FROM user u JOIN studentmarksmapping smm ON u.userid = smm.studentid WHERE smm.rollnumber = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    String rollNumberParam = request.getParameter("rollnumber");
                    int rollNumber = Integer.parseInt(rollNumberParam); 
                    stmt.setInt(1, rollNumber);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            Student student = new Student();
                            student.setUserId(rs.getInt("userid"));
                            student.setRollNumber(rs.getInt("rollnumber"));
                            student.setName(rs.getString("firstname") + " " + rs.getString("lastname"));
                            student.setEnglish(rs.getInt("english"));
                            student.setCs(rs.getInt("cs"));
                            student.setMaths(rs.getInt("maths"));
                            student.setPhysics(rs.getInt("physics"));
                            student.setChemistry(rs.getInt("chemistry"));
                            studentList.add(student);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("username", username);
        request.setAttribute("role", role);
        request.setAttribute("studentList", studentList);

        request.getRequestDispatcher("/editstudentmarks.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rollNumberStr = request.getParameter("rollnumber");
        String englishMarksStr = request.getParameter("english");
        String csMarksStr = request.getParameter("cs");
        String mathsMarksStr = request.getParameter("maths");
        String physicsMarksStr = request.getParameter("physics");
        String chemistryMarksStr = request.getParameter("chemistry");

        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String sql = "UPDATE studentmarksmapping SET english = ?, cs = ?, maths = ?, physics = ?, chemistry = ? WHERE rollnumber = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, parseMarks(englishMarksStr));
                    stmt.setInt(2, parseMarks(csMarksStr));
                    stmt.setInt(3, parseMarks(mathsMarksStr));
                    stmt.setInt(4, parseMarks(physicsMarksStr));
                    stmt.setInt(5, parseMarks(chemistryMarksStr));
                    int rollNumber = parseRollNumber(rollNumberStr);
                    stmt.setInt(6, rollNumber);

                    System.out.println("Roll Number being set in PreparedStatement: " + rollNumber);


                    int result = stmt.executeUpdate();
                    if (result > 0) {
                        response.sendRedirect("StudentListStaffServlet");
                    } else {
                        response.getWriter().println("Error Updating Marks");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    private int parseMarks(String marksStr) {
        if (marksStr == null || marksStr.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(marksStr);
    }

    private int parseRollNumber(String rollNumberStr) {
        if (rollNumberStr == null || rollNumberStr.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(rollNumberStr);
    }
}