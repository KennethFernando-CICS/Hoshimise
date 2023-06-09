package controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    public static Connection con;
    public static ResultSet rs;
    protected int ctr = 1;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Class.forName(getServletContext().getInitParameter("jdbcClassName")); //load driver
            String username = getServletContext().getInitParameter("dbUserName"), //get connection parameters from web.xml
                    password = getServletContext().getInitParameter("dbPassword"),
                    driverURL = getServletContext().getInitParameter("jdbcDriverURL");
            con = DriverManager.getConnection(driverURL, username, password); //create connection object
        } catch (SQLException sqle) {
            System.out.println("SQLException error occured - " + sqle.getMessage());
        } catch (ClassNotFoundException nfe) {
            System.out.println("ClassNotFoundException error occured - " + nfe.getMessage());
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();       
        boolean temp = false;
        String userEmail = request.getParameter("email"), userPass = model.Encryption.encrypt(request.getParameter("password"),
                getServletContext().getInitParameter("key"), getServletContext().getInitParameter("cipher"));

        try{
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT USERNAME, EMAIL, PASSWORD FROM USERS");
            
            while(rs.next()){
                String emailDB = rs.getString("EMAIL").trim();
                
                System.out.println(String.format("Email: %s || Username: %s || Password: %s", emailDB, rs.getString("USERNAME"), 
                        rs.getString("PASSWORD"))); //print the contents of the database into the console
                
                if(userEmail.equals(emailDB) && userPass.equals(rs.getString("PASSWORD").trim())){
                    session.setAttribute("username", rs.getString("USERNAME"));
                    System.out.println("Found user in the database");
                    temp = true;
                    break;
                }
            }
            
            if(temp){ //verifies if the user is in the database and redirect them to the homepage
                ctr = 0;
                response.sendRedirect("index.jsp");
            }
            else if(ctr < 3){ //shows a message that the email or password is wrong (total of 3 tries)
                ctr++;
                request.setAttribute("succ", "true"); //NOTE: FOR VERIFICATION FOR POPUP IN THE LOGIN PAGE
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else{ //throw an error message which will redirect the user to error 440 page
                ctr = 0;
                response.sendError(440);
            }
        } catch(SQLException e){
            e.printStackTrace();
            response.sendError(500);
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
