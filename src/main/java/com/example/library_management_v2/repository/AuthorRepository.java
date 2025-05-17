package com.example.library_management_v2.repository;

import com.example.library_management_v2.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Redan skapade metoder finns i JpaRepository

}