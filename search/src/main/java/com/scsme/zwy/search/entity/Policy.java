package com.scsme.zwy.search.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "policy", type = "policy_table")
public class Policy {

  public static final String CONTENT = "content";
  public static final String TITLE = "title";
  public static final String RELEASE_ID = "releaseId";
  public static final String UPDATE_TIME = "updateTime";
  public static final String TAGS = "tags";

  private Integer releaseId;
  private String title;
  private String content;
  private List<String> tags;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;

  private String highlighterContent;

  @Override
  public String toString() {
    return "Policy{"
        + "releaseId="
        + releaseId
        + ", title='"
        + title
        + '\''
        + ", tags="
        + tags
        + ", updateTime="
        + updateTime
        + ", highlighterContent='"
        + highlighterContent
        + '\''
        + ", highlighterTitle='"
        + highlighterTitle
        + '\''
        + '}';
  }

  private String highlighterTitle;
}
