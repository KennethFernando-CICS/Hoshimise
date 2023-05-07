package controller;

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
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(logPrefix + "Hoshimise Web Application Shut Down. ");
      
    }
    
    
}
