package kea.dilemmaspilbackend.game.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {


    @GetMapping("")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("HELLO");
    }

}
