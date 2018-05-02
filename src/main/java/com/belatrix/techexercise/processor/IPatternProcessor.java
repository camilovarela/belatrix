/**
 * Copyright 2018, Belatrix
 * http://www.grupodot.com
 * 
 * All rights reserved Date: 02/05/2018
 */
package com.belatrix.techexercise.processor;

import java.util.List;

public interface IPatternProcessor {
  
  /**
   * 
   * @param text
   * @return
   */
  List<String> extractMatches(String text);
}
