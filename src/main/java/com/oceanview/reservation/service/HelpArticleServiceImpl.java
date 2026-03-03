package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.HelpArticleDAO;
import com.oceanview.reservation.model.HelpArticle;

import java.util.List;

public class HelpArticleServiceImpl implements HelpArticleService{

    private final HelpArticleDAO helpArticleDAO = new HelpArticleDAO();

    @Override
    public List<HelpArticle> getAllArticle() {
        return helpArticleDAO.findAll();
    }

    @Override
    public HelpArticle getArticleById(int articleId) {
        return helpArticleDAO.findById(articleId);
    }
}
