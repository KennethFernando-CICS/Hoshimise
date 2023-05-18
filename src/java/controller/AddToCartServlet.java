package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import model.Cart;
import model.CartItem;

public class AddToCartServlet extends HttpServlet {
    String prefix = "[AddToCart]";
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
        try ( PrintWriter out = response.getWriter()) {
            conn = connectDB(getServletContext());
            String username = (String) request.getSession().getAttribute("username");
            Cart cart = getCart(username);
            String size = request.getParameter("size");
            int qty = Integer.parseInt(request.getParameter("quantity"));
            int prodId = Integer.parseInt(request.getParameter("prodId"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            
            qty = (qty > stock) ? stock : qty;
            size = (size == null) ? "N/A" : size;
            
            cart.addToCart(new CartItem(prodId, size), qty);
            writeCart(username, cart);
            response.sendRedirect("ProductDetails?id=" + prodId);
            
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
     * Get cart from DB
     * @param username
     * @return Cart of username
     */
    public Cart getCart(String username){
        try {
            String query = "SELECT CART FROM USERS WHERE USERNAME = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();           
            
            String json = rs.getString("CART");
            Gson gson = new Gson();
            Cart currentCart = gson.fromJson(json, Cart.class);
            System.out.println(prefix + "Current Cart Size: " + currentCart.getCartItemMap().size());
            
            return currentCart;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
                ps.close();
            } catch (Exception e) {}
        }
        return null;
    }
    
    /**
     * Update cart in database
     * @param username
     * @param userCart to write
     */
    public void writeCart(String username,Cart userCart){
        try {    
            String query = "UPDATE USERS SET CART = ? WHERE USERNAME = ?";
            ps = conn.prepareStatement(query);
            
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            String newCartJson = gson.toJson(userCart);
            ps.setString(1, newCartJson);
            ps.setString(2, username);
            ps.executeUpdate();
                                 
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            try {
                rs.close();
                ps.close();
            } catch (Exception e) {}
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

}
