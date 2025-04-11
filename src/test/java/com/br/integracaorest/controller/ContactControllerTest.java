package com.br.integracaorest.controller;

import com.br.integracaorest.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContact() {
        String contactData = "{\"email\":\"test@example.com\"}";
        when(contactService.createContact(contactData)).thenReturn(Mono.just("Contato criado"));

        Mono<String> resultMono = contactController.createContact(contactData);
        String result = resultMono.block();

        assertEquals("Contato criado", result);
    }

    @Test
    void testListContacts() {
        when(contactService.getAllContacts()).thenReturn(Mono.just("Lista de contatos"));

        Mono<String> resultMono = contactController.listContacts();
        String result = resultMono.block();

        assertEquals("Lista de contatos", result);
    }
}