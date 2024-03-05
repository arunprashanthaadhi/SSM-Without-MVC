import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddUserServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/final";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserRole> roles = new ArrayList<>(); 

        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String query = "SELECT * FROM userrole"; 
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            UserRole role = new UserRole(); 
                            role.setUserRoleId(rs.getInt("userroleid")); 
                            role.setUserRoleName(rs.getString("userrolename")); 
                            roles.add(role);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("roles", roles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adduser.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String rollNumber = request.getParameter("rollnumber");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contactno");
        String address = request.getParameter("address");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String about = request.getParameter("about");
        int roleId = Integer.parseInt(request.getParameter("userRole")); 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = -1;
        boolean isStudentRole = false;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            String roleQuery = "SELECT userrolename FROM userrole WHERE userroleid = ?";
            stmt = conn.prepareStatement(roleQuery);
            stmt.setInt(1, roleId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String roleName = rs.getString("userrolename");
                if ("student".equalsIgnoreCase(roleName)) {
                    isStudentRole = true;
                }
            }
            rs.close();
            stmt.close();

            String query = "INSERT INTO user (userroleid, firstname, lastname, rollnumber, dob, email, contactno, address, username, password, about) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, roleId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, rollNumber);
            stmt.setString(5, dob);
            stmt.setString(6, email);
            stmt.setString(7, contactNo);
            stmt.setString(8, address);
            stmt.setString(9, username);
            stmt.setString(10, password);
            stmt.setString(11, about);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }
            
            if (isStudentRole && userId != -1) {
                String marksQuery = "INSERT INTO studentmarksmapping (studentid, english, cs, maths, physics, chemistry, rollnumber) VALUES (?, 0, 0, 0, 0, 0, ?)";
                stmt.close(); 
                stmt = conn.prepareStatement(marksQuery);
                stmt.setInt(1, userId);
                stmt.setString(2, rollNumber);
                stmt.executeUpdate();
            }

            response.sendRedirect("addUser.jsp?success=true");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("addUser.jsp?success=false");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}