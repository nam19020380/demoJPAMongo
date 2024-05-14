package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordRequest {
    @NotBlank
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }
}