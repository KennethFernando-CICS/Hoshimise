package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PurchaseServlet extends HttpServlet {
    String prefix = "[Purchase]";
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
            conn = connectDB(getServletContext());
            if(request.getParameterValues("selected") == null){
                response.sendRedirect("cart.jsp");
            }
            String[] selected = request.getParameterValues("selected");
            //Adding Selected Products to Purchased List
            Map<String, Map<Integer, Double>> boughtMap = new HashMap<>();
            for (String item : selected){
                String[] itemDetails = item.split("-");
                rs = getProduct(Integer.parseInt(itemDetails[0]));
                rs.next();
                String name = rs.getString("NAME");
                double price = rs.getDouble("PRICE");
                double totalPrice = Math.round(price * Integer.parseInt(itemDetails[2]) * 100.0) / 100.0;
                String purItem = name + " - " + itemDetails[1];
                boughtMap.put(purItem, new HashMap<>());
                boughtMap.get(purItem).put(Integer.valueOf(itemDetails[2]), totalPrice);
                System.out.println(prefix + "Added: " + name + " - " + itemDetails[1] + " $ "+ itemDetails[2]);
            }            
            //Removing Selected Products from Cart
            rd = request.getRequestDispatcher("CartTakeOut");
            rd.include(request, response);
            //Send to Success Purchase Page
            request.getSession().setAttribute("selectedTotalPrice", request.getParameter("selectedTotalPrice"));
            request.getSession().setAttribute("boughtMap", boughtMap);
            response.sendRedirect("success_page_buy.jsp");
        } catch(Exception e) {
            
        } finally{
            try{
                rs.close();
                ps.close();
                conn.close();
                System.out.println(prefix + "SQL Objects Closed.");
            } catch(Exception e) {}
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
     * Get product from database using id
     * @param id of product
     * @return ResultSet with product details
     */
    public ResultSet getProduct(int id){
        try {
            String query = "SELECT * FROM PRODUCTS WHERE PROD_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            return rs;            
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
