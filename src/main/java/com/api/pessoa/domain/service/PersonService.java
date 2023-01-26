package com.api.pessoa.domain.service;

import com.api.pessoa.domain.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    public PersonDTO create(PersonDTO personDTO);

    List<PersonDTO> list();
}
