package controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = connectDB(); //connect to the database
        HttpSession session = request.getSession();

        String username = request.getParameter("username"), email = request.getParameter("email"),
                password = model.Encryption.encrypt(request.getParameter("password"),
                        getServletContext().getInitParameter("key"), getServletContext().getInitParameter("cipher"));
        
        int id = 1;

        try {       
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT USERNAME, EMAIL FROM APP.USERS");
            while (rs.next()) {
                id++;
                if (username.equals(rs.getString("USERNAME"))) { //if the username is already in the database
                    request.setAttribute("fail", 1);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else if (email.equals(rs.getString("EMAIL"))) { //if the email is already is in the database
                    request.setAttribute("fail", 2);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }
            
            PreparedStatement ps = conn.prepareStatement("INSERT INTO APP.USERS VALUES (?, ?, ?, ?, '')");
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.executeUpdate();
            session.setAttribute("username", username);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static Connection connectDB() {
        Connection conn = null;
        try {
            String driver = "org.apache.derby.jdbc.ClientDriver";
            Class.forName(driver);
            System.out.println("LOADED DRIVER: " + driver);

            String url = "jdbc:derby://localhost:1527/FapDB";
            String username = "app";
            String password = "app";
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
