package com.example.library_management_v2.dto;

import java.time.LocalDate;

public class UserDTO {

    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    private LocalDate registrationDate;

    // En tom konstruktor som Spring kräver
    public UserDTO() {
    }

    // Setters och Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
