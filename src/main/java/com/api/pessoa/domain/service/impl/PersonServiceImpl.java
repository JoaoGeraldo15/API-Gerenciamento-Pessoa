package com.api.pessoa.domain.service.impl;

import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.PersonMapper;
import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.repository.PersonRepository;
import com.api.pessoa.domain.service.PersonService;
import com.api.pessoa.domain.service.exeception.EntityNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    private PersonMapper personMapper;

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        Person personSaved = personRepository.save(personMapper.toEntity(personDTO));
        return personMapper.toDTO(personSaved);
    }

    @Override
    public List<PersonDTO> list() {
        return personMapper.listToDTO(personRepository.findAll());
    }

    @Override
    public PersonDTO getPersonById(Long idPerson) {
        return personMapper.toDTO(findPersonOrThrowException(idPerson));
    }

    @Override
    public PersonDTO update(Long idPerson, PersonDTO personDTO) {
        findPersonOrThrowException(idPerson);
        personDTO.setId(idPerson);
        return save(personDTO);
    }

    @Override
    public void delete(Long idPerson) {
        Person person = findPersonOrThrowException(idPerson);
        personRepository.delete(person);
    }

    private Person findPersonOrThrowException(Long idPerson) {
        return personRepository.findById(idPerson).orElseThrow(() -> new EntityNotFound(String.format("Person with id %d not found ", idPerson)));
    }
}
