package controller;

import com.google.gson.GsonBuilder;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.Cart;

public class RegisterServlet extends HttpServlet {
    String prefix = "[Register]";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    RequestDispatcher rd = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        conn = connectDB(request.getServletContext()); //connect to the database
        HttpSession session = request.getSession();

        String username = request.getParameter("username"), email = request.getParameter("email"),
                password = model.Encryption.encrypt(request.getParameter("password"),
                        getServletContext().getInitParameter("key"), getServletContext().getInitParameter("cipher"));
                            
        try {     
            int id = getId(); 
            
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT USERNAME, EMAIL FROM APP.USERS");
            while (rs.next()) {
                if (username.equals(rs.getString("USERNAME"))) { //if the username is already in the database
                    request.setAttribute("fail", 1);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else if (email.equals(rs.getString("EMAIL"))) { //if the email is already is in the database
                    request.setAttribute("fail", 2);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }
            Cart cart = new Cart(username);
            String cartJson = new GsonBuilder().enableComplexMapKeySerialization().create().toJson(cart);
            
            PreparedStatement ps = conn.prepareStatement("INSERT INTO APP.USERS VALUES (?, ?, ?, ?, ?)"); //edit nalang ito para sa cart
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5,cartJson);
            ps.executeUpdate();
            session.setAttribute("username", username);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } finally{
            try{
                rs.close();
                ps.close();
                conn.close();
                System.out.println(prefix + "SQL Objects Closed.");
            } catch(Exception e) {}
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
    /**
     * Gets connection from database
     * @param sc ServletContext
     * @return Connection
     */
    public Connection connectDB(ServletContext sc){
        Connection conn = null;       
        try {
            String driver = sc.getInitParameter("jdbcClassName");
            Class.forName(driver);
            
            String url = sc.getInitParameter("jdbcDriverURL");
            String username = sc.getInitParameter("dbUserName");
            String password = sc.getInitParameter("dbPassword");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println(prefix + "Connection success.");
        }
        catch (Exception e){
            e.printStackTrace();
        }        
        return conn;
    }
    
    /**
     * Get new user id for the user
     * @return 
     */
    public int getId(){
        try {
            String getID = "SELECT MAX(USER_ID) AS ID FROM USERS";
            ps = conn.prepareStatement(getID);
            rs = ps.executeQuery();
            int id = 0;
            if(rs.next())
                id = rs.getInt("ID") + 1;
            System.out.println(prefix + "ID:" + id);
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
