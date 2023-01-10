package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDto {

    @NotNull(message = "Firstname cant be null")
    @Size(min = 2, message = "Firstname must be longer than 2")
    private String customerFirstName;

    @NotNull(message = "Lastname cant be null")
    @Size(min = 2, message = "Lastname must be longer than 2")
    private String customerLastName;

    @NotNull(message = "Email cant be null")
    @Email(message = "Incorrect email address")
    @Size(message = "Email must be longer than 2")
    private String email;

}
