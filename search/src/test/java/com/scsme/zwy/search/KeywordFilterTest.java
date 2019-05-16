package com.scsme.zwy.search;

import com.scsme.zwy.search.wordfilter.WordFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordFilterTest {
    @Test
    public void testKeywordFilter() throws Exception{
        String result = WordFilter.replace("习近平", 2, '*');
        System.out.printf(result);
        assert result != null;
    }
}
