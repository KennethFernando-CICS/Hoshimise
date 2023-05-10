package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cart;
import model.CartItem;

public class CartTakeOutServlet extends HttpServlet {
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
            Cart userCart = getCart(username);
            
            String[] itemsToRemove = request.getParameterValues("selected");
            System.out.println(Arrays.toString(itemsToRemove));
            for(String item: itemsToRemove){
                String[] curItem = item.split("-");
                userCart.takeOutFromCart(new CartItem(Integer.parseInt(curItem[0]),curItem[1]));
            }
            writeCart(getServletContext(),username, userCart);
            
            response.sendRedirect("cart.jsp");
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Connection connectDB(ServletContext sc){
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
    
    public void writeCart(ServletContext sc,String username,Cart userCart){
        try {
            String query = "SELECT CART FROM USERS WHERE USERNAME = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();           
            
            Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
            String newCartJson = gson.toJson(userCart);
            System.out.println();
            String path = sc.getRealPath("") + "/carts/" + rs.getString("CART");
            
            PrintWriter pw = new PrintWriter(new FileOutputStream(path));
            pw.print(newCartJson);
            pw.close();
            System.out.println("Write?\n" + newCartJson);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Cart getCart(String username){
        try {
            String query = "SELECT CART FROM USERS WHERE USERNAME = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();           
            
            String json = readFile(rs.getString("CART"));;
            Gson gson = new Gson();
            Cart currentCart = gson.fromJson(json, Cart.class);
            return currentCart;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String readFile(String fileName){
       String json = "";
        try {
            String path = "/carts/"+ fileName;
            InputStream is = getServletContext().getResourceAsStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = bf.readLine()) != null){
                json += line;
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return json;
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
