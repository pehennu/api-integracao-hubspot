package com.br.integracaorest.controller;

import com.br.integracaorest.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService service;

    @PostMapping("/create")
    public Mono<String> createContact(@RequestBody String contactData) {
        return service.createContact(contactData);
    }

    @GetMapping("/list")
    public Mono<String> listContacts() {
        return service.getAllContacts();
    }
}
