package com.scsme.zwy.articlesearch.util;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JestClientUtil {
    private static String spring_elasticsearch_jest_uris;
    private static Integer spring_elasticsearch_jest_read_timeout;
    private static Integer Spring_elasticsearch_jest_connection_timeout;
    @Value("${spring.elasticsearch.jest.uris[0]}")
    public void setSpring_elasticsearch_jest_uris(String spring_elasticsearch_jest_uris) {
        JestClientUtil.spring_elasticsearch_jest_uris = spring_elasticsearch_jest_uris;
    }
    @Value("${spring.elasticsearch.jest.read-timeout}")
    public void setSpring_elasticsearch_jest_read_timeout(Integer spring_elasticsearch_jest_read_timeout) {
        JestClientUtil.spring_elasticsearch_jest_read_timeout = spring_elasticsearch_jest_read_timeout;
    }
    @Value("${spring.elasticsearch.jest.connection-timeout}")
    public void setGetSpring_elasticsearch_jest_connection_timeout(Integer Spring_elasticsearch_jest_connection_timeout) {
        JestClientUtil.Spring_elasticsearch_jest_connection_timeout = Spring_elasticsearch_jest_connection_timeout;
    }
    public static JestClient getJestClient(){
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(spring_elasticsearch_jest_uris).connTimeout(Spring_elasticsearch_jest_connection_timeout).readTimeout(spring_elasticsearch_jest_read_timeout).multiThreaded(true).build());
        return factory.getObject();
    }
}
