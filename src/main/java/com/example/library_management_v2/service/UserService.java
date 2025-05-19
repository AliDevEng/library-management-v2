package com.example.library_management_v2.service;

import com.example.library_management_v2.dto.UserDTO;
import com.example.library_management_v2.entity.User;
import com.example.library_management_v2.exception.UserNotFoundException;
import com.example.library_management_v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


    public UserDTO getUserByEmail (String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Användare med email: " + email + " hittades inte!") );

        return convertToDTO(user);
    }


    /**
     * Konverterar en User entity till UserDTO (utan lösenord)
     * user User entity att konvertera
     * returnera Konverterad UserDTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        // Lösenordet inkluderas inte i DTO:n av säkerhetsskäl
        return dto;
    }
}
