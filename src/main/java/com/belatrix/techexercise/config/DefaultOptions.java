/**
 * Copyright 2018, Belatrix
 * http://www.grupodot.com
 * 
 * All rights reserved Date: 02/05/2018
 */
package com.belatrix.techexercise.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DefaultOptions {
  
  private String location;
}