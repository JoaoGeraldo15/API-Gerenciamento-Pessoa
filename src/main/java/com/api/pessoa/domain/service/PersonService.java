package com.api.pessoa.domain.service;

import com.api.pessoa.domain.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    public PersonDTO save(PersonDTO personDTO);

    List<PersonDTO> list();

    PersonDTO getPersonById(Long idPerson);

    PersonDTO update(Long idPerson, PersonDTO personDTO);
}
