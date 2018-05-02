/**
 * Copyright 2018, Belatrix http://www.grupodot.com
 * 
 * All rights reserved Date: 02/05/2018
 */
package com.belatrix.techexercise;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.belatrix.techexercise.config.DefaultOptions;
import com.belatrix.techexercise.processor.IPatternProcessor;
import com.belatrix.techexercise.reader.IUrlReader;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the main method which is used to execute the pipeline.
 * 
 * @since 1.0.0
 * @version 1.0.0
 * @author <a href="ing.camilovarela@gmail.com">Camilo Varela</a>
 */
@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

  @Autowired
  private IUrlReader urlReader;

  @Autowired
  private IPatternProcessor patternProcessor;

  private static final String FILE_LOCATION_PROPERTY = "file.location";

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {

    if (!args.getOptionNames().contains(FILE_LOCATION_PROPERTY)) {
      throw new RuntimeException("You must specify the file.location property.");
    }

    String location = args.getOptionValues(FILE_LOCATION_PROPERTY).get(0);
    File tmpFile = new File(location);

    if (!tmpFile.exists()) {
      throw new RuntimeException("You must specify a valid file.location.");
    }

    List<String> urlsToProcess =
        this.urlReader.retreiveUrls(DefaultOptions.builder().location(location).build());
    urlsToProcess.parallelStream().forEach(p -> {
      processUrl(p);
    });
  }

  /**
   * Process the given URL. It analyzes the main page of the url.
   * 
   * @param stringUrl The given url
   */
  private void processUrl(String stringUrl) {

    List<String> matches = Collections.emptyList();
    try {
      URL url = new URL(stringUrl);
      URLConnection con = url.openConnection();
      InputStream in = con.getInputStream();
      String encoding = con.getContentEncoding();
      encoding = encoding == null ? "UTF-8" : encoding;
      String body = IOUtils.toString(in, encoding);

      matches = this.patternProcessor.extractMatches(body);

    } catch (Exception e) {
      log.error("There was an error trying to process url: [{}]", stringUrl, e);
    }

    if (!matches.isEmpty()) {

      try (PrintWriter pw =
          new PrintWriter(new FileWriter("bltrx-" + extractWebsiteName(stringUrl) + ".txt"));) {
        matches.stream().forEach(p -> {
          pw.println(p);
        });
      } catch (Exception e) {
        log.error("There was an error generating result file for url: [{}]", stringUrl, e);
      }
    }
  }

  /**
   * Extracts the website name
   * 
   * @param url The given url
   * @return The website name of the given url
   */
  private String extractWebsiteName(String url) {

    String website = url.replace("http://", "");
    website = website.replace("https://", "");
    website = website.replace("www.", "");
    return website.substring(0, website.indexOf(".com"));
  }
}
