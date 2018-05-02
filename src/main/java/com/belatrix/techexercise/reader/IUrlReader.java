/**
 * Copyright 2018, Belatrix
 * http://www.grupodot.com
 * 
 * All rights reserved Date: 02/05/2018
 */
package com.belatrix.techexercise.reader;

import java.util.List;
import com.belatrix.techexercise.config.DefaultOptions;

public interface IUrlReader {
  
  List<String> retreiveUrls(DefaultOptions config);
}
