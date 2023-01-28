package com.api.pessoa.domain.model.mapper;

import com.api.pessoa.domain.model.Address;
import com.api.pessoa.domain.model.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "personId", target = "person.id")
    Address toEntity(AddressDTO dto);
    List<Address> toEntity(List<AddressDTO> dto);

    @Mapping(source = "person.id", target = "personId")
    AddressDTO toDTO(Address entity);
    List<AddressDTO> toDTO(List<Address> entities);
}
