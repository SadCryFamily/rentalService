package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRentalDto {
    @NotNull(message = "Name cant be null")
    @Size(min = 4, message = "Name must be longer than 4")
    private String rentalName;

    @NotNull(message = "Description cant be null")
    @Size(min = 15, message = "Description must be longer than 15")
    private String rentalDescription;

    @Min(value = 100, message = "Price must be greater than 100$")
    @NotNull(message = "Price cant be null")
    private BigDecimal rentalPrice;

}
