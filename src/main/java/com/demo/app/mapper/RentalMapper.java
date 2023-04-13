package com.demo.app.mapper;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    Rental toRental(CreateRentalDto rentalDto);

    PreviewRentalDto toPreviewRentalDto(Rental rental);

    ViewRentalDto toViewRentalDto(Rental rental);

}
