/**
 * Copyright 2018, Belatrix
 * http://www.grupodot.com
 * 
 * All rights reserved Date: 02/05/2018
 */
package com.belatrix.techexercise.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class HashTagPattern implements IPatternProcessor {

  /*
   * (non-Javadoc)
   * 
   * @see com.belatrix.techexercise.IPatternProcessor#extractMatches(java.lang.String)
   */
  @Override
  public List<String> extractMatches(String text) {
    
    Pattern pattern = Pattern.compile("#([a-zA-Z_]+)");
    Matcher matcher = pattern.matcher(text);

    Set<String> allMatches = new HashSet<>();
    while (matcher.find()) {
      allMatches.add(matcher.group());
    }
    return new ArrayList<>(allMatches);
  }
}
