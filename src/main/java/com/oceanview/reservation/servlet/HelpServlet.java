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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // auth check
        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !(role.equals("STAFF") || role.equals("ADMIN"))) {
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        String articleId = request.getParameter("id");

        if (articleId == null) {
            request.setAttribute("articles", helpArticleService.getAllArticle());
            request.getRequestDispatcher("/help/helpList.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(articleId);
        request.setAttribute("article", helpArticleService.getArticleById(id));
        request.getRequestDispatcher("/help/helpView.jsp").forward(request, response);
    }
}
