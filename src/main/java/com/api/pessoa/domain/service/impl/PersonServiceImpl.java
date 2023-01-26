package com.api.pessoa.domain.service.impl;

import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.PersonMapper;
import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.repository.PersonRepository;
import com.api.pessoa.domain.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    private PersonMapper personMapper;

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        Person personSaved = personRepository.save(personMapper.toEntity(personDTO));
        return personMapper.toDTO(personSaved);
    }
}
