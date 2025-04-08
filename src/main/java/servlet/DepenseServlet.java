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
import models.Depense;
import models.Prevision;


public class DepenseServlet extends HttpServlet {
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int idPrevision = Integer.parseInt(req.getParameter("libelle"));
        int montant = Integer.parseInt(req.getParameter("montant"));
        HttpSession s = req.getSession();
        int id = (int)s.getAttribute("id");
        Depense d = new Depense(idPrevision, montant, id);
        List<Prevision> previsions = new ArrayList<>();
       try {
        List<Prevision> temporaire = (List<Prevision>)(List<?>)new Prevision().findAll();
        for (Prevision prevision : temporaire) {
            if(prevision.getIdUser() == id){
                previsions.add(prevision);
            }
        }}
        catch(Exception e){
            throw new ServletException(e.getMessage());
        }

        try {
            Prevision temporaire = (Prevision)new Prevision().findById(idPrevision);
            temporaire.setId(idPrevision);
            if(temporaire.getMontant() - montant > 0){
                Prevision prevision = new Prevision(temporaire.getLibelle(), temporaire.getMontant() - montant, id);
                prevision.setId(idPrevision);
                d.save(d);
                // temporaire.update(prevision);
                req.setAttribute("previsions", previsions);
            }else{
                req.setAttribute("previsions", previsions);
                req.setAttribute("error", "votre montant doit etre inférieur à votre prévision");
            }
            RequestDispatcher dispat = req.getRequestDispatcher("depense.jsp");
            dispat.forward(req,res);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
       List<Prevision> previsions = new ArrayList<>();
       HttpSession s = req.getSession();
       int id = (int)s.getAttribute("id");
       try {
        List<Prevision> temporaire = (List<Prevision>)(List<?>)new Prevision().findAll();
        for (Prevision prevision : temporaire) {
            if(prevision.getIdUser() == id){
                previsions.add(prevision);
            }
        }
            req.setAttribute("previsions", previsions);
            RequestDispatcher dispat = req.getRequestDispatcher("depense.jsp");
            dispat.forward(req,res);
    } catch (Exception e) {
        throw new ServletException(e.getMessage());
    }
    }
}
