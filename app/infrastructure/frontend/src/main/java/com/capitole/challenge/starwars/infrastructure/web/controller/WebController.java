package com.capitole.challenge.starwars.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

  @GetMapping({"/", "/index"})
  public String index() {
    // Forward to static index.html bundled from the frontend module
    return "forward:/index.html";
  }
}
