package com.example.rentalfilm.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotNull
    @Size(min = 3, message = "Nama harus lebih dari 2 karakter")
    private String nama;

    @NotNull(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotNull
    @Size(min = 6, message = "Password harus lebih dari 5 karakter")
    private String pass;

    private String role;

    public User() {
    }
}
