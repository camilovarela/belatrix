/**
 * Copyright 2018, Belatrix
 * http://www.grupodot.com
 * 
 * All rights reserved Date: 02/05/2018
 */
package com.belatrix.techexercise.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.belatrix.techexercise.config.DefaultOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalFileUrlReader implements IUrlReader {

  /*
   * (non-Javadoc)
   * 
   * @see com.belatrix.techexercise.IUrlReader#retreiveUrls(com.belatrix.techexercise.config.
   * DefaultConfiguration)
   */
  @Override
  public List<String> retreiveUrls(DefaultOptions config) {
    
    List<String> urls = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(config.getLocation()))) {
      String url;
      while ((url = br.readLine()) != null) {
        urls.add(url);
      }
    } catch (Exception e) {
      log.error("There was an error reading the input file.", e);
    }
    return urls;
  }
}
