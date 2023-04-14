package com.demo.app.mapper;

import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.RetrieveCustomerDto;
import com.demo.app.dto.UpdateCustomerDto;
import com.demo.app.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CreateCustomerDto toCreateCustomerDto(Customer customer);

    RetrieveCustomerDto toRetrieveCustomerDto(Customer customer);

    Customer toCustomer(CreateCustomerDto customerDto);

    Customer toCustomer(UpdateCustomerDto customerDto);

    Customer toCustomer(RetrieveCustomerDto customerDto);
}
