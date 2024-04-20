package crud_app.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * class for reading the database properties file after the application starts
 */
public class MyServletContextListener implements ServletContextListener {
    /**
     * method read property file
     *
     * @param servletContextEvent servlet context
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InputStream inputStreamProperty = null;
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            inputStreamProperty = servletContext.getResourceAsStream("/WEB-INF/classes/app.properties");
            Properties properties = new Properties();
            properties.load(inputStreamProperty);
            DataBase.setProperties(properties);
            DataBase.initDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamProperty != null) {
                try {
                    inputStreamProperty.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
