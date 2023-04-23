package com.demo.app.mapper;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.UpdateRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    Rental toRental(CreateRentalDto rentalDto);

    Rental toRental(ViewRentalDto rentalDto);

    Rental toRental(PreviewRentalDto rentalDto);

    Rental toRental(UpdateRentalDto rentalDto);

    PreviewRentalDto toPreviewRentalDto(Rental rental);

    ViewRentalDto toViewRentalDto(Rental rental);

}
