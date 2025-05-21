package com.example.library_management_v2.controller;

//  Vi skapar en controller för att hantera lån-relaterade endpoints:
import com.example.library_management_v2.dto.LoanDTO;
import com.example.library_management_v2.exception.UserNotFoundException;
import com.example.library_management_v2.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/users")
public class LoanController {

    @Autowired
    private LoanService loanService;


    // Hämta alla lån för en specifik användare
    @GetMapping("/{userId}/loans")
    public ResponseEntity<List<LoanDTO>> getUserLoans(@PathVariable Long userId) {
        try {
            List<LoanDTO> loans = loanService.getUserLoans(userId);
            return ResponseEntity.ok(loans);
        } catch (UserNotFoundException e) {

            // Vi låter GlobalExceptionHandler hantera detta
            throw e;
        }
    }
}