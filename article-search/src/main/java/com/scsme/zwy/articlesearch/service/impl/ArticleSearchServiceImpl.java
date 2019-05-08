package com.scsme.zwy.articlesearch.service.impl;

import com.scsme.zwy.articlesearch.commonJson.ExtLimit;
import com.scsme.zwy.articlesearch.entity.Article;
import com.scsme.zwy.articlesearch.service.ArticleSearchService;
import com.scsme.zwy.articlesearch.util.JestClientUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleSearchServiceImpl implements ArticleSearchService
{
    private static final String QUERY_FIELD_KEY="content";
    private static final String TAGS_FIELD_KEY="tags";
    private static final String INDEX_NAME="zwy_article";
    private static final String TYPE_NAME="zwy_article_table";
    @Override
    public JSONObject findAllArticleByElasticsearch(Map<String,Object> requestMap, ExtLimit limit) throws Exception {
        JSONObject json = new JSONObject();
        JestClient jestClient = null;
        try {
            jestClient = JestClientUtil.getJestClient();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" {\"query\": { \"match\": { \"").append(QUERY_FIELD_KEY).append("\":\"").append(requestMap.get(QUERY_FIELD_KEY).toString())
                    .append("\"}},\"")
                    .append("size\":").append(limit.getPagesize()).append(",\"from\":").append(limit.getPagestart())
                    .append(",\"highlight\":{\"pre_tags\":[\"<tag1>\",\"<tag2>\"],\"post_tags\":[\"</tag1>\",\"</tag2>\"],\"fields\":{\"*\":{}}}}");
            json = searchForArticle(jestClient, stringBuilder.toString());
            jestClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private static JSONObject searchForArticle(JestClient jestClient, String query) throws Exception {
        JSONObject result = new JSONObject();
        Search search = new Search.Builder(query)
                .addIndex(INDEX_NAME)
                .addType(TYPE_NAME)
                .build();
        JestResult jr = jestClient.execute(search);
        JsonObject jsonObject = jr.getJsonObject();
        JsonObject hitsObject = jsonObject.getAsJsonObject("hits");
        long took = jsonObject.get("took").getAsLong();
        long total = hitsObject.get("total").getAsLong();
        JsonArray jsonArray = hitsObject.getAsJsonArray("hits");
        System.out.println("took:" + took + "  " + "total:" + total);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonHitsObject = jsonArray.get(i).getAsJsonObject();
            JsonObject sourceObject = jsonHitsObject.get("_source").getAsJsonObject();
            Article article = new Article();
            article.setItemid(Integer.parseInt(sourceObject.get("itemid").getAsNumber().toString()));
            article.setContent(sourceObject.get("content").getAsString());
            articles.add(article);
        }
        result.put("articles", articles);
        return result;
    }
}
