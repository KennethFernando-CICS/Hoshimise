package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            
            Connection conn = connectDB();

            String query = "SELECT * FROM PRODUCTS FETCH FIRST 10 ROWS ONLY";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                System.out.println("Loaded Product ID:" + rs.getInt("PROD_ID"));
                out.println("<div class=\"item-container\">\n" +
                            "<div class=\"temp-image\">" +
                            "<img class=\"item-img\" src=\"resources/images/" + rs.getString("IMAGE") + "\"/>" +
                            "</div>" +
                            "<h1 class=\"item-name truncate\">" + rs.getString("Name") + "</h1>\n" +
                            "<p class=\"price\">$" + rs.getDouble("Price") + "</p>\n" +
                            "</div>"
                );
            }            
            
            //Close
            rs.close();
            stmt.close();
            conn.close();
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection connectDB()
    {
        Connection conn = null;
        try {
            String driver = "org.apache.derby.jdbc.ClientDriver";
            Class.forName(driver);
            System.out.println("LOADED DRIVER: " + driver);
            
            String url = "jdbc:derby://localhost:1527/FapDB";
            String username = "app";
            String password = "app";
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
