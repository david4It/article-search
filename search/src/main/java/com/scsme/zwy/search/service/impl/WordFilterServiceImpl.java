package com.scsme.zwy.search.service.impl;

import com.scsme.zwy.search.service.WordFilterService;
import com.scsme.zwy.search.wordfilter.WordFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordFilterServiceImpl implements WordFilterService {
  @Value("${word-filter.keyword.distance}")
  private Integer distance;

  @Value("${word-filter.keyword.symbol}")
  private Character symbol;

  @Override
  public List<String> replaceContent(List<String> sources) {
    if (sources != null && sources.size() > 0) {
      return sources.stream()
          .map(source -> WordFilter.replace(source, distance, symbol))
          .collect(Collectors.toList());
    }
    return null;
  }
}
