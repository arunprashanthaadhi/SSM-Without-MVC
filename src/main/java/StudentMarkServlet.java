import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class StudentMarkServlet extends HttpServlet {

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

       
        if (session == null || username == null || !"Student".equals(role)) {
            response.sendRedirect("login.jsp"); 
            return;
        }


        List<Student> studentList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "SELECT smm.*,u.* FROM studentmarksmapping AS smm JOIN user AS u ON smm.studentid = u.userid Where username=?";

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);

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
                            
                            System.out.println("studentList------------->>>>>>>>>" + studentList);
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
        
        request.getRequestDispatcher("/studentmarks.jsp").forward(request, response);
    }
}
