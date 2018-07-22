package ua.kiev.prog;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAppServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("JPATest");
        servletContextEvent
                .getServletContext()
                .setAttribute("emf", emf);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        EntityManagerFactory emf = (EntityManagerFactory)servletContextEvent
                .getServletContext()
                .getAttribute("emf");
        emf.close();
    }
}
