package com.api.pessoa.domain.model;

import com.api.pessoa.domain.model.dto.PersonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper {

    Person toEntity(PersonDTO dto);

    PersonDTO toDTO(Person entity);
}
