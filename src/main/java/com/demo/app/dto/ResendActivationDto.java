package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResendActivationDto {

    @NotNull(message = "Email cant be null")
    @Email(message = "Incorrect email address")
    @Size(min = 10, message = "Email must be longer than 10")
    private String customerEmail;

}
