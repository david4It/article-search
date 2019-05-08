package com.scsme.zwy.articlesearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "zwy_article",type = "zwy_article_table")
public class Article {
    private Integer itemid;
    private String content;
}
