package ua.kiev.prog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        add(false, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        add(true, req, resp);
    }

    private void add(boolean redirect,
                     HttpServletRequest req,
                     HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        int iAge = 0;

        try {
            iAge = Integer.parseInt(age);
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        UserDAO dao = new UserDAOImpl(em);
        try {
            dao.add(name, iAge);
        } finally {
            em.close();
        }

        if (redirect) {
            resp.sendRedirect("list");
        } else {
            resp.getWriter().print("OK");
        }
    }
}
