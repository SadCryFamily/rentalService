package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetrieveCustomerDto {

    private String customerFirstName;

    private String customerLastName;

    private String customerUsername;

}
