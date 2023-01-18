package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginCustomerDto {

    @NotNull(message = "Username cant be null")
    @Size(min = 5, max = 30, message = "Username must be from 5 to 30")
    private String customerUsername;

    @NotNull(message = "Password cant be null")
    @Size(min = 7, max = 30, message = "Password must be from 7 to 30")
    private String customerPassword;

}
