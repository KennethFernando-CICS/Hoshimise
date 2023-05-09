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
        ServletContext sc = sce.getServletContext();
        //Initializing sorting method used
        String[] productType = {"T-Shirt","Hoodie"};
        String[] anime = {"Demon Slayer","Attack on Titan"};
        String sortType = "product";
        
        List<String> categoryList = null;
        if(sortType.equals("product"))
            categoryList = Arrays.asList(productType); 
        else if(sortType.equals("anime"))
            categoryList = Arrays.asList(anime);
               
        sc.setAttribute("categories", categoryList);
        sc.setAttribute("sortType", sortType);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(logPrefix + "Hoshimise Web Application Shut Down. ");
      
    }
    
    
}
