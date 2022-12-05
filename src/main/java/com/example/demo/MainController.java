 package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @GetMapping("/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Well done");
    }
    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("Well done");
    }
}
