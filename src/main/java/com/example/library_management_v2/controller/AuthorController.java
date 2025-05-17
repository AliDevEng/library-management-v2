package com.example.library_management_v2.controller;

import com.example.library_management_v2.dto.AuthorDTO;
import com.example.library_management_v2.exception.AuthorNotFoundException;
import com.example.library_management_v2.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    /**
     * GET /authors - Hämtar alla författare
     * @return Lista med alla författare som AuthorDTO
     */
    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        // Anropa service-metoden för att hämta alla författare
        return authorService.getAllAuthors();
    }

    // Hitta författare via efternamn
    @GetMapping("/name/{lastName}")
    public ResponseEntity<List<AuthorDTO>> getAuthorsByLastName(@PathVariable String lastName) {

        try {
            List<AuthorDTO> authors = authorService.getAuthorsByLastName(lastName);
            return ResponseEntity.ok(authors);
        } catch (AuthorNotFoundException e) {

            // Låt GlobalExceptionHandler hantera detta
            throw e;


        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}