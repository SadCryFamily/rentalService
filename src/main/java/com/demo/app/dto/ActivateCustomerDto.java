package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivateCustomerDto {

    @NotNull(message = "Username cant be null")
    @Size(min = 5, message = "Username must be longer than 5")
    private String username;

    @NotNull(message = "Activation code cant be null")
    private BigDecimal activationCode;

}
