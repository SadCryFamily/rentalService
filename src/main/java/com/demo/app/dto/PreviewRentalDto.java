package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreviewRentalDto {

    @NotNull(message = "Name cant be null")
    @Size(min = 4, message = "Name must be longer than 4")
    private String rentalName;

    @NotNull(message = "City cant be null")
    @NotBlank(message = "City cant be blank")
    private String rentalCity;

    @Min(value = 15, message = "Area must be greater than 15m^2")
    @NotNull(message = "Area cant be null")
    private int rentalArea;

    @Min(value = 100, message = "Price must be greater than 100$")
    @NotNull(message = "Price cant be null")
    private BigDecimal rentalPrice;

}
