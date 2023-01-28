package com.api.pessoa.domain.model.mapper;

import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.dto.PersonDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper {

    Person toEntity(PersonDTO dto);

    PersonDTO toDTO(Person entity);
    List<PersonDTO> listToDTO(List<Person> entities);
}
