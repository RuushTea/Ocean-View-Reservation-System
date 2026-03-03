package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.HelpArticle;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HelpArticleDAO {

    // List all help articles from the DB
    public List<HelpArticle> findAll(){
        String sql = "SELECT articleid, title, content FROM help_article";
        List<HelpArticle> list = new ArrayList<>();

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                list.add(new HelpArticle(
                        rs.getInt("articleId"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }
        } catch (Exception e){
            System.out.println("Failed to load help articles: " + e.getMessage());
        }

        return list;
    }

    // Find a help article by the ID from the DB
    public HelpArticle findById(int articleId){
        String sql = "SELECT articleid, title, content FROM help_article WHERE articleid = ?";
        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, articleId);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new HelpArticle(
                            rs.getInt("articleId"),
                            rs.getString("title"),
                            rs.getString("content")
                    );
                }
            }
        } catch (Exception e){
            System.out.println("Failed to load help article: " + e.getMessage());
        }

        return null;
    }
}
