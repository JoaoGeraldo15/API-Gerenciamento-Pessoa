package com.api.pessoa.domain.model.mapper;

import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDTO dto);

    List<Person> toEntity(List<PersonDTO> entities);
    PersonDTO toDTO(Person entity);
    List<PersonDTO> toDTO(List<Person> entities);
}
