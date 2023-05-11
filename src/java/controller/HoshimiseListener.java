package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * 
 */
public class HoshimiseListener implements ServletContextListener {
    
    private final String logPrefix = "[HoshimiseListener]";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
        try {
            System.out.println(logPrefix + "Hoshimise Web Application Initialized. ");
            ServletContext sc = sce.getServletContext();
            List<String> categoryList = new ArrayList<>();
            conn = connectDB(sc);
            String query = "SELECT DISTINCT(CATEGORY) FROM PRODUCTS";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                categoryList.add(rs.getString("CATEGORY"));
            }            
            sc.setAttribute("categories", categoryList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try{
                rs.close();
                stmt.close();
                conn.close();
                System.out.println(logPrefix + "SQL Objects Closed.");
            } catch(SQLException e){}
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(logPrefix + "Hoshimise Web Application Shut Down. ");
      
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
}
