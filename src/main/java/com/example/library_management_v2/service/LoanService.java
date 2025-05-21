package com.example.library_management_v2.service;


// Vi skapar vi en service-klass för att hantera lånerelaterade operationer

import com.example.library_management_v2.dto.LoanDTO;
import com.example.library_management_v2.entity.Loan;
import com.example.library_management_v2.entity.User;
import com.example.library_management_v2.exception.UserNotFoundException;
import com.example.library_management_v2.repository.LoanRepository;
import com.example.library_management_v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    // Hämta alla lån för en specifik user
    public List<LoanDTO> getUserLoans (Long userId) {

        // Vi kontrollerar om användaren finns:
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException ("Användare med ID: " + userId + " hittades inte"));

        // Hämta användarens lån
        List<Loan> loans = loanRepository.findByUserId(userId);


        // Konvertera och returnera lån som DTO:
        return loans.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // Vi behöver konvertera en Loan entity till LoanDTO med utökad information
    private LoanDTO convertToDTO (Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUser().getId());
        dto.setBookId(loan.getBook().getId());
        dto.setBookTitle(loan.getBook().getTitle());

        // Sätt författarnamn om tillgängligt
        if (loan.getBook().getAuthor() != null) {
            String authorName =
                    loan.getBook()
                            .getAuthor()
                            .getFirstName() + " " +
                            loan.getBook().getAuthor().getLastName();
            dto.setAuthorName(authorName);
        }


        dto.setBorrowedDate(loan.getBorrowedDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnedDate(loan.getReturnedDate());
        dto.setActive(loan.isActive());


        // Beräkna och kontrollera om lånet är försenat
        if (loan.isActive() && loan.getDueDate() != null) {
            dto.setOverdue(LocalDate.now().isAfter(loan.getDueDate()));
        } else {
            dto.setOverdue(false);
        }

        return dto;
    }


}
