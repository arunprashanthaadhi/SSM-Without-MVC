import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class EditProfileStaffServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");

        if (userid != null && !userid.isEmpty()) {
            try {
                String dbURL = "jdbc:mysql://localhost:3306/final";
                String user = "root";
                String pass = "";

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(dbURL, user, pass);

                String sql = "SELECT * FROM user JOIN studentmarksmapping ON user.userid=studentmarksmapping.studentid WHERE userid = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(userid));

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    request.setAttribute("userid", rs.getInt("userid"));
                    request.setAttribute("rollnumber", rs.getInt("rollnumber"));
                    request.setAttribute("firstname", rs.getString("firstname"));
                    request.setAttribute("lastname", rs.getString("lastname"));
                    request.setAttribute("dob", rs.getString("dob"));
                    request.setAttribute("email", rs.getString("email"));
                    request.setAttribute("address", rs.getString("address"));
                    request.setAttribute("about", rs.getString("about"));
                    RequestDispatcher dispatcher = request.getRequestDispatcher("editstudentstaff.jsp");
                    dispatcher.forward(request, response);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String useridStr = request.getParameter("userid");
        String rollnumberStr = request.getParameter("rollnumber");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String about = request.getParameter("about");

        try {
            String dbURL = "jdbc:mysql://localhost:3306/final";
            String user = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, user, pass);

            String sql = "UPDATE user SET  firstname = ?, lastname = ?, dob = ?, email = ?, address = ?, about = ? WHERE userid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, dob);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, about);
            stmt.setInt(7, Integer.parseInt(useridStr)); 

            System.out.println("Executing SQL query: " + stmt);
            int result = stmt.executeUpdate();
            if (result > 0) {
                response.sendRedirect("StudentListServlet");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Error Updating Profile");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
