package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewRentalDto {

    @Min(value = 1, message = "Id cant be lower than 1")
    @NotNull(message = "Id cant be null")
    private Long rentalId;

    @NotNull(message = "Photo cant be null")
    private String photoUrl;

    @NotNull(message = "Name cant be null")
    @Size(min = 4, message = "Name must be longer than 4")
    private String rentalName;

    @NotNull(message = "City cant be null")
    @NotBlank(message = "City cant be blank")
    private String rentalCity;

    @NotNull(message = "Address cant be null")
    @Size(min = 4, message = "Address must be longer than 4")
    private String rentalAddress;

    @NotNull(message = "Description cant be null")
    @Size(min = 15, message = "Description must be longer than 15")
    private String rentalDescription;

    @Min(value = 15, message = "Area must be greater than 15m^2")
    @NotNull(message = "Area cant be null")
    private int rentalArea;

    @Min(value = 100, message = "Price must be greater than 100$")
    @NotNull(message = "Price cant be null")
    private BigDecimal rentalPrice;

}
