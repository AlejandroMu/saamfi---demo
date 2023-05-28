package co.edu.icesi.dev.saamfi.example.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/public/demo")
    public String helloWorld() {
        return "hello world without auth";
    }

    @GetMapping("/private/demo/authentication")
    public String helloWorldAut() {
        return "hello world with auth but no role";
    }

    @GetMapping("/private/demo/authorization")
    @PreAuthorize("hasAnyRole('Demo-Role')")
    public String helloWorldAutRole() {
        return "hello world with auth and Demo-Role";
    }

}
