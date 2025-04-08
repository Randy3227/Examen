package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Prevision;

public class PrevisionServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
        res.setContentType("text/html");
        HttpSession s = req.getSession();
        String libelle = req.getParameter("libelle");
        int montant = Integer.parseInt(req.getParameter("montant"));
        int id = (int)s.getAttribute("id");
        Prevision prevision = new Prevision(libelle, montant, id);
        try {
            prevision.save(prevision);
            res.sendRedirect("prevision.jsp");
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
       
    }
}
