package controller;

import java.util.Arrays;
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
        System.out.println(logPrefix + "Hoshimise Web Application Initialized. "); 
        String[] categories = {"T-Shirt","Hoodies"};
        List<String> categoryList = Arrays.asList(categories);
        
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("categories", categoryList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(logPrefix + "Hoshimise Web Application Shut Down. ");
      
    }
    
    
}
