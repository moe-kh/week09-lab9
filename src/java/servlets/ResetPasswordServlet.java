/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import services.GmailService;

/**
 *
 * @author 794456
 */
public class ResetPasswordServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        AccountService ac = new AccountService();

        UserDB userdb = new UserDB();
        HttpSession session = request.getSession();
        User user = null;
        if (action.equals("sendemail")) {
            String toEmail = request.getParameter("resetemail");

            try {
                List<User> allUsers = userdb.getAll();
                for (User u : allUsers) {
                    if (u.getEmail().equals("cprg352+" + toEmail)) {
                        user = u;
                        session.setAttribute("username", user.getUsername());
                        break;
                    }
                }
                if (user != null) {

                    String subject = "Reset password";
                    String template = getServletContext().getRealPath("/WEB-INF") + "/emailtemplates/login.html";
                    HashMap<String, String> tags = new HashMap<>();
                    tags.put("firstname", user.getFirstname());
                    tags.put("lastname", user.getLastname());
                    tags.put("username", user.getUsername());
                    tags.put("link", ac.resetPassword(getServletContext().getRealPath("/WEB-INF")));
                    GmailService.sendMail(toEmail, subject, template, tags);
                    request.setAttribute("wrong", "password for email has been sent successfully!");
                } else {
                    request.setAttribute("wrong", "reenter email!");
                    getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
                }
            } catch (NotesDBException ex) {
                Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Mail mail = new Mail();
            //Session session = new Session();
            //mail.sendHTMLEmail("abc@gmail.com", "123password", toEmail, "HEllO WORLD", "<h1>Hello world</h1>");
            //Message message = new MimeMessage(session);

        } else {
            String newPassword = request.getParameter("newpassword");

            try {
                user = userdb.getUser((String) session.getAttribute("username"));
                ac.changePassword(user.getResetPasswordUID(), newPassword);
            } catch (NotesDBException ex) {
                Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
