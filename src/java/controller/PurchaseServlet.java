package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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
            String owner = (String) request.getSession().getAttribute("username");
            String[] selected = request.getParameterValues("selected");
            String selTotalPrice = request.getParameter("selectedTotalPrice");
            //Adding Selected Products to Purchased List
            Map<String, Map<Integer, Double>> boughtMap = new HashMap<>();
            for (String item : selected){
                String[] itemDetails = item.split("-");
                
                //Update stock numbers
                String stockUpdate = "UPDATE PRODUCTS SET STOCK = (STOCK - ?) WHERE PROD_ID = ?";
                PreparedStatement psStock = conn.prepareStatement(stockUpdate);
                psStock.setInt(1, Integer.parseInt(itemDetails[2]));
                psStock.setInt(2, Integer.parseInt(itemDetails[0]));
                psStock.executeUpdate();
                psStock.close();
                
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
            
            //Add Record to Database
            int tran_id = createTransactionRecord(owner, selected, selTotalPrice);
            
            //Send to Success Purchase Page
            HttpSession session = request.getSession();
            session.setAttribute("tran_id", tran_id);
            session.setAttribute("selectedTotalPrice", selTotalPrice);
            session.setAttribute("boughtMap", boughtMap);
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
    /**
     * Adds transaction record to database
     * @param owner of cart
     * @param selectedItems to purchase
     * @param selTotalPrice 
     */
    private int createTransactionRecord(String owner,String[] selectedItems, String selTotalPrice) {
        int id = 0;
        try {
            //Transaction ID
            String getID = "SELECT MAX(TRAN_ID) AS ID FROM TRANSACTIONS";
            ps = conn.prepareStatement(getID);
            rs = ps.executeQuery();            
            if(rs.next())
               id = rs.getInt("ID") + 1; 
            System.out.println(prefix + "Transaction ID:" + id);
            
            //Bought items cart
            Cart boughtCart = new Cart(owner);
            int itemId;
            String size;
            int qty;
            for (String item : selectedItems){
                System.out.println(prefix + item);
                String[] itemDetails = item.split("-");
                itemId = Integer.parseInt(itemDetails[0]);
                size = itemDetails[1];
                qty = Integer.parseInt(itemDetails[2]);
                boughtCart.addToCart(new CartItem(itemId, size), qty);
            }            
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            String cartJson = gson.toJson(boughtCart);
            //Total Price
            double totalPrice = Double.parseDouble(selTotalPrice);
            
            //Date Time of Purchase
            Timestamp dateOfPurchase = new Timestamp(new Date().getTime());
            
            String query = "INSERT INTO TRANSACTIONS (TRAN_ID, ITEMS, TOTAL, DATETIME) VALUES (?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, cartJson);
            ps.setDouble(3, totalPrice);
            ps.setTimestamp(4, dateOfPurchase);
            ps.executeUpdate();
                                               
        } catch (Exception e) {
          e.printStackTrace();
        } 
    return id;
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
