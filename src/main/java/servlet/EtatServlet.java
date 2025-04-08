package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Prevision;

public class EtatServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
        
    }
    
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{ 
        HttpSession s = req.getSession();
        int id = (int)s.getAttribute("id");
        List<Prevision> previsions = new ArrayList<>();
      try {
       previsions = (List<Prevision>)(List<?>)new Prevision().findAll();
      }
       catch(Exception e){
           throw new ServletException(e.getMessage());
       }
        req.setAttribute("id", id);
        req.setAttribute("previsions", previsions);
        RequestDispatcher dispat = req.getRequestDispatcher("etat.jsp");
            dispat.forward(req,res);
    }
}
