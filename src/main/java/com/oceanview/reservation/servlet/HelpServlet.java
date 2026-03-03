package com.oceanview.reservation.servlet;

import com.oceanview.reservation.service.HelpArticleService;
import com.oceanview.reservation.service.HelpArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelpServlet", value = "/help")
public class HelpServlet extends HttpServlet {

    private final HelpArticleService helpArticleService = new HelpArticleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // auth check
        String role = (String) req.getSession().getAttribute("role");
        if (role == null || !(role.equals("STAFF") || role.equals("ADMIN"))) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String idParam = req.getParameter("id");

        if (idParam == null) {
            req.setAttribute("articles", helpArticleService.getAllArticle());
            req.getRequestDispatcher("/help/helpList.jsp").forward(req, resp);
            return;
        }

        int id = Integer.parseInt(idParam);
        req.setAttribute("article", helpArticleService.getArticleById(id));
        req.getRequestDispatcher("/help/helpView.jsp").forward(req, resp);
    }
}
