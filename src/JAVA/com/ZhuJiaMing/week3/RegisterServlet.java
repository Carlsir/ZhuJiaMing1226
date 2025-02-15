package JAVA.com.ZhuJiaMing.week3;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(
        urlPatterns = "/register",
        loadOnStartup = 1
)
public class RegisterServlet extends HttpServlet {
    Connection conn = null;

    @Override
    public void init() throws ServletException {
        super.init();
       /* ServletContext context = getServletContext();
        String driver = context.getInitParameter("driver");
        String url = context.getInitParameter("url");
        String username = context.getInitParameter("username");
        String password = context.getInitParameter("password");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connection --> "+conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        conn = (Connection) getServletContext().getAttribute("conn");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String birthdate = request.getParameter("birthdate");

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
       /* out.println("<html>");
        out.println("   <head>");
        out.println("       <title>UsersTable</title>");
        out.println("   </head>");
        out.println("   <body>");
        out.println("       <table width=540 border=1 align=center>");
        out.println("           <caption>Users</caption>");
        out.println("           <tr>");
        out.println("               <td>ID</td>");
        out.println("               <td>UserName</td>");
        out.println("               <td>PassWord</td>");
        out.println("               <td>Email</td>");
        out.println("               <td>Gender</td>");
        out.println("               <td>BirthDate</td>");
        out.println("           </tr>");*/

        String sql = "INSERT INTO Users(username,password,email,gender,birthdate) VALUES(?,?,?,?,?)";
        try {
            //这里不用插id是因为在数据库的表中设置为了标识增量（自增1，第一行默认插入1）
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,email);
            ps.setString(4,gender);
            ps.setDate(5, Date.valueOf(birthdate));
            ps.executeUpdate();

            //rs = stmt.executeQuery(sql2);
            /*while (rs.next()){
                int id1 = rs.getInt("id");
                String username1 = rs.getString("username");
                String password1 =rs.getString("password");
                String email1 = rs.getString("email");
                String gender1 = rs.getString("gender");
                String birthdate1 = rs.getString("birthdate");
                out.println("           <tr>");
                out.println(               "<td>"+id1+"</td>");
                out.println(               "<td>"+username1+"</td>");
                out.println(               "<td>"+password1+"</td>");
                out.println(               "<td>"+email1+"</td>");
                out.println(               "<td>"+gender1+"</td>");
                out.println(               "<td>"+birthdate1+"</td>");
                out.println("           </tr>");
            }*/
            /*request.setAttribute("rsname",rs);
            request.getRequestDispatcher("userList.jsp").forward(request,response);*/
            //after register a new user -> user can login
            response.sendRedirect("login");//LoginServlet
            /*rs.close();
            stmt.close();*/
            //System.out.println("after forward");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*out.println("       </table>");
        out.println("   </body>");
        out.println("</html>");*/
        out.close();


    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

