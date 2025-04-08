package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

public class AuthentificationServlet extends HttpServlet{
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
        res.setContentType("text/html");
        HttpSession s = req.getSession();
        String name = req.getParameter("name");
        String pwd = req.getParameter("password");
        List<User> users= null;
        int id = 0;
        try {
            users = (List<User>)(List<?>)new User().findAll();
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
        String name_attribute = "";
        String pwd_attribute = "";
        boolean is_authenticate = false;
        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(pwd)) {
                name_attribute = name;
                pwd_attribute = pwd;
                id = user.getId();
                is_authenticate = true;
                break;
            }
        }
        if(is_authenticate){
            s.setAttribute("name", name);
            s.setAttribute("pwd", pwd);
            s.setAttribute("id", id);
            res.sendRedirect("Authenticate");
        }
        else{
            res.sendRedirect("Authenticate");
        }
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/html");
        HttpSession s = req.getSession();
        PrintWriter out = res.getWriter();
        if((String)s.getAttribute("name") != null && (String)s.getAttribute("pwd") != null && (String)s.getAttribute("pwd")!= "" && (String)s.getAttribute("name") != ""){
            res.sendRedirect("prevision.jsp");
        }else{
            s.setAttribute("error", "incorrect");
            res.sendRedirect("index.jsp");
        }
    }
}
