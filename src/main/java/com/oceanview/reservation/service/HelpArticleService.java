package com.oceanview.reservation.service;

import com.oceanview.reservation.model.HelpArticle;

import java.util.List;

public interface HelpArticleService {
    List<HelpArticle> getAllArticle();
    HelpArticle getArticleById(int articleId);
}
