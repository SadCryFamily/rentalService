package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCustomerDto {

    @NotNull(message = "Username cant be null")
    @Min(value = 5, message = "Username must be longer than 5")
    @Max(value = 20, message = "Username must be longer than 20")
    private String customerUsername;

    @NotNull(message = "Password cant be null")
    @Min(value = 7, message = "Password must be longer than 7")
    @Max(value = 30, message = "Password must be lower than 30")
    private String customerPassword;

}
