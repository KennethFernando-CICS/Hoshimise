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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
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
            String fileName = getFileName(username);
            Cart userCart = getCart(fileName);
            
            if(request.getParameterValues("selected") == null){
                response.sendRedirect("cart.jsp");
            }
            if(request.getParameterValues("selected") != null){
                String[] itemsToRemove = request.getParameterValues("selected");
                System.out.println("[CartTakeOut]" + Arrays.toString(itemsToRemove));
                for(String item: itemsToRemove){
                    String[] curItem = item.split("-");
                    userCart.takeOutFromCart(new CartItem(Integer.parseInt(curItem[0]),curItem[1]));
                }
                writeCart(getServletContext(),fileName, userCart);
                int newSize = userCart.getCartItemMap().size();
                System.out.println("[CartTakeOut]Remaining Items: " + newSize);
                userCart = getCart(fileName); 
                LocalDateTime start = LocalDateTime.now();
                while(userCart.getCartItemMap().size() != newSize){
                   userCart = getCart(fileName);
                }              
                LocalDateTime end = LocalDateTime.now();
                System.out.println("[CartTakeOut]Took " + (end.getSecond() - start.getSecond()) + " seconds to delete.");                
            }
            response.sendRedirect("cart.jsp");
            
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                //Close
                rs.close();
                ps.close();
                conn.close();
                System.out.println("[ProductLoad]SQL Objects Closed.");
            } catch(SQLException e){
                System.out.println("[ProductLoad]SQL Objects Failed to Close.");
            }
        }
    }
    
    public Connection connectDB(ServletContext sc){
        Connection conn = null;       
        try {
            String driver = sc.getInitParameter("jdbcClassName");
            Class.forName(driver);
            
            String url = sc.getInitParameter("jdbcDriverURL");
            String username = sc.getInitParameter("dbUserName");
            String password = sc.getInitParameter("dbPassword");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("[CartTakeOut]Connection success.");
        }
        catch (Exception e){
            e.printStackTrace();
        }        
        return conn;
    }
    
    public String getFileName(String username){
        try {
            String query = "SELECT CART FROM USERS WHERE USERNAME = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();
            String filename = rs.getString("CART");
            rs.close();
            ps.close();
            return filename;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void writeCart(ServletContext sc,String fileName,Cart userCart){
        String path = sc.getRealPath("") + "/carts/" + fileName;
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(path));) {          
            
            Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
            String newCartJson = gson.toJson(userCart);
            System.out.println();
           
            pw.print(newCartJson);           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Cart getCart(String fileName){          
        String json = readFile(fileName);
        Gson gson = new Gson();
        Cart currentCart = gson.fromJson(json, Cart.class);
        
        return currentCart;
    }
    
    public String readFile(String fileName){
       String json = "";
       String path = "/carts/"+ fileName;
        try (InputStream is = getServletContext().getResourceAsStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));){           
            String line;
            while((line = bf.readLine()) != null){
                json += line;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
