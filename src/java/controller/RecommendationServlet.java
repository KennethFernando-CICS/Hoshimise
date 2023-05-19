package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecommendationServlet extends HttpServlet {
    String prefix = "[ProductRecommendation]";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    RequestDispatcher rd = null;
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
        try {   
            PrintWriter out = response.getWriter();
            conn = connectDB(getServletContext());
            int id = Integer.parseInt(request.getParameter("id"));
            String category = getProductCategory(id);
            System.out.println(prefix + "Category: " + category);
            
            String query = "SELECT * FROM PRODUCTS WHERE CATEGORY = ? AND NOT PROD_ID = ? ORDER BY RANDOM() FETCH FIRST 5 ROWS ONLY";
            ps = conn.prepareStatement(query);
            ps.setString(1, category);
            ps.setInt(2, id);
            rs = ps.executeQuery();
            while(rs.next()){
               out.print(
                    "<div class=\"product\" onClick=\"window.location.href ='ProductDetails?id=" + rs.getInt("PROD_ID") + "'\">\n" +
                        "<img alt=\"product image\" src=\"resources/images/" + rs.getString("IMAGE") + "\">\n" +
                        "<span>" + rs.getString("NAME") + "</span>" +
                    "</div>"               
               );
            }
            
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                if(rs != null)
                    rs.close();
                if(ps != null)
                    rs.close();
                if(conn != null)
                    conn.close();
                System.out.println(prefix + "SQL Objects Closed.");
            } catch(SQLException e){}
        }
    }

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
     * Get product category from database using id
     * @param id of product
     * @return ResultSet with product details
     */
    public String getProductCategory(int id){
        try {
            String query = "SELECT CATEGORY FROM PRODUCTS WHERE PROD_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            rs.next();
            return rs.getString("CATEGORY");            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
        return null;
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
