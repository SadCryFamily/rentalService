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
public class UpdateCustomerDto {

    @NotNull(message = "Firstname cant be null")
    @Size(min = 3, max = 15, message = "Firstname must be longer thsn 3")
    private String customerFirstName;

    @NotNull(message = "Lastname cant be null")
    @Size(min = 3, max = 15, message = "Lastname must be longer than 3")
    private String customerLastName;

    @NotNull(message = "Password cant be null")
    @Size(min = 7, message = "Password must be longer than 7")
    private String customerPassword;

}
