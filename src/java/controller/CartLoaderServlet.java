package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;

public class CartLoaderServlet extends HttpServlet {
    String prefix = "[CartLoader]";
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
        try{
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession();
            conn = connectDB(request.getServletContext());
            String username = (String) session.getAttribute("username");
            
            if(username != null){
                printItems(out,request,response);            
            }else{
                response.sendRedirect("login.jsp");
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
    /**
     * Display all items in cart
     * @param out writer to page
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public void printItems(PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws IOException{
        Cart currentCart = getCart((String) request.getSession().getAttribute("username"));
        Map<CartItem,Integer> cartContents = currentCart.getCartItemMap();
        int i = 0;
        if(currentCart.getCartItemMap().size() == 0){
            response.getWriter().print(
            "<div style=\"width: 80%; display: flex;align-items:center;justify-content: center;color:white;size:5rem;\">\n" +
                "<br><h1>Your cart is empty</h1>\n" +
                "<img style=\"vertical-align:middle;\" height=\"360\" src=\"resources/logo/empty_CArT.png\"/>\n<br>" +
            "</div>"
            );
        }
        for (Map.Entry<CartItem,Integer> entry: cartContents.entrySet()) {
            try {
                CartItem cartItem = entry.getKey();
                int quantity = entry.getValue();
                rs = getProduct(cartItem.getProductId());
                rs.next();
                String cartCardUrl = "cartItemCard.jsp" +
                        "?itemName=" + rs.getString("NAME") +
                        "&imgName=" + rs.getString("IMAGE") +
                        "&price=" + rs.getDouble("PRICE") +
                        "&stock=" + rs.getInt("STOCK") +
                        "&quantity=" + quantity +
                        "&size=" + cartItem.getSize() +
                        "&itemId=" + cartItem.getProductId() + "-" + cartItem.getSize() + "-" + quantity;
                
                rd = request.getRequestDispatcher(cartCardUrl);
                rd.include(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally{
                try {
                    rs.close();
                    ps.close();
                } catch (Exception e) {}
            }
            i++;
        }
        System.out.println(prefix + "Showing: " + i + " items.");
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
