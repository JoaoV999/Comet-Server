package com.cometproject.server.game.landing;

import com.cometproject.server.game.landing.types.PromoArticle;
import com.cometproject.server.storage.queries.landing.LandingDao;
import javolution.util.FastMap;

import java.util.Map;

public class LandingManager {
    private Map<Integer, PromoArticle> articles;

    public LandingManager() {
        this.loadArticles();
    }

    public void loadArticles() {
        if(this.articles != null) {
            this.articles.clear();
        }

        this.articles = LandingDao.getArticles();
    }

    public Map<Integer, PromoArticle> getArticles() {
        return articles;
    }
}