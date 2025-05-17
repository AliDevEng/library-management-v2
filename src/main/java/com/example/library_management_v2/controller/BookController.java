package com.example.library_management_v2.controller;

import com.example.library_management_v2.dto.BookDTO;
import com.example.library_management_v2.dto.BookWithDetailsDTO;
import com.example.library_management_v2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * GET /books - Hämtar alla böcker
     * @return Lista med alla böcker som BookWithDetailsDTO
     */
    @GetMapping
    public List<BookWithDetailsDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * GET /books/search - Söker böcker baserat på titel eller författare
     * @param title Titeln att söka efter (frivillig)
     * @param author Författarens efternamn att söka efter (frivillig)
     * @return Lista med matchande böcker
     */
    @GetMapping("/search")
    public List<BookWithDetailsDTO> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        return bookService.searchBooks(title, author);
    }

    /**
     * POST /books - Skapar en ny bok
     * @param bookDTO Data för den nya boken
     * @return Den skapade boken som BookDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }
}