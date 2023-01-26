package com.api.pessoa.domain.service;

import com.api.pessoa.domain.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    public PersonDTO create(PersonDTO personDTO);
}
