package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerDto {

    @NotNull(message = "Firstname cant be null")
    @Size(min = 3, max = 15, message = "Firstname must be longer thsn 3")
    private String customerFirstName;

    @NotNull(message = "Lastname cant be null")
    @Size(min = 3, max = 15, message = "Lastname must be longer than 3")
    private String customerLastName;

    @NotNull(message = "Username cant be null")
    @Size(min = 5, message = "Username must be longer than 5")
    private String customerUsername;

    @NotNull(message = "Email cant be null")
    @Email(message = "Incorrect email address")
    @Size(min = 10, message = "Email must be longer than 10")
    private String customerEmail;

    @NotNull(message = "Password cant be null")
    @Size(min = 7, message = "Password must be longer than 7")
    private String customerPassword;

}
