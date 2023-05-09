package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductLoadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            PrintWriter out = response.getWriter();
            int count = Integer.parseInt(request.getParameter("count"));
            
            Connection conn = connectDB(getServletContext());

            String query = "SELECT * FROM PRODUCTS FETCH FIRST ? ROWS ONLY";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, count);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                System.out.println("[ProductLoad]Loaded Product ID:" + rs.getInt("PROD_ID"));
                out.println("<div class=\"item-container\">\n" +
                            "<div class=\"temp-image\">" +
                            "<img class=\"item-img\" src=\"resources/images/" + rs.getString("IMAGE") + "\"/>" +
                            "</div>" +
                            "<h1 class=\"item-name\">" + rs.getString("Name") + "</h1>\n" +
                            "<p class=\"price\">$" + rs.getDouble("Price") + "</p>\n" +
                            "</div>"
                );
            }            
            
            //Close
            rs.close();
            ps.close();
            conn.close();
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection connectDB(ServletContext sc)
    {
        Connection conn = null;       
        try {
            String driver = sc.getInitParameter("jdbcClassName");
            Class.forName(driver);
            System.out.println("LOADED DRIVER: " + driver);
            
            String url = sc.getInitParameter("jdbcDriverURL");
            String username = sc.getInitParameter("dbUserName");
            String password = sc.getInitParameter("dbPassword");
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        return conn;
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

}
