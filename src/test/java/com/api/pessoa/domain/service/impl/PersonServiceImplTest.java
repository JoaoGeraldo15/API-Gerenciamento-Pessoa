package com.api.pessoa.domain.service.impl;

import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.model.mapper.PersonMapper;
import com.api.pessoa.domain.repository.PersonRepository;
import com.api.pessoa.domain.service.exeception.EntityNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static com.api.pessoa.domain.commom.PersonConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Test
    public void createPerson_WithValidData_ShouldReturnPlanetSaved() {
        when(personMapper.toEntity(PERSON)).thenReturn(PERSON_ENTITY);
        when(personMapper.toDTO(PERSON_ENTITY)).thenReturn(PERSON);
        when(personRepository.save(PERSON_ENTITY)).thenReturn(PERSON_ENTITY);

        // sut -> System Under Test
        PersonDTO sut = personService.save(PERSON);

        assertThat(sut).isEqualTo(PERSON);
    }

    @Test
    public void createPerson_WithInvalidData_ShouldThrowConstraintViolationException() {
        when(personMapper.toEntity(INVALID_PERSON)).thenReturn(INVALID_PERSON_ENTITY);
        when(personRepository.save(INVALID_PERSON_ENTITY)).thenThrow(ConstraintViolationException.class);

        assertThatThrownBy(() -> personService.save(INVALID_PERSON)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void listPerson_ReturnsAllPersons() {

        when(personMapper.toDTO(PERSONS_ENTITIES_ID)).thenReturn(PERSONS_ID);
        when(personRepository.findAll()).thenReturn(PERSONS_ENTITIES_ID);

        List<PersonDTO> sut = personService.list();

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PERSON_ID);
    }

    @Test
    void getPersonById_WithExistingPersonId_ShouldReturnPerson() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(PERSON_ENTITY_ID));
        when(personMapper.toDTO(PERSON_ENTITY_ID)).thenReturn(PERSON_ID);

        PersonDTO sut = personService.getPersonById(PERSON_ENTITY_ID.getId());
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PERSON_ID);
    }

    @Test
    void getPersonById_WithNonExistingPersonId_ShouldThrowEntityNotFound() {
        when(personRepository.findById(anyLong())).thenThrow(EntityNotFound.class);

        assertThatThrownBy(() -> personService.getPersonById(PERSON_ENTITY_ID.getId())).isInstanceOf(EntityNotFound.class);
    }

    @Test
    void updatePerson_WithExistingPersonId_ShouldReturnsUpdatedPerson() {
        PersonDTO person = PERSON;
        person.setName("João Sales");

        Person personModified = PERSON_ENTITY_ID;
        personModified.setName("João Sales");

        when(personMapper.toEntity(person)).thenReturn(personModified);
        when(personMapper.toDTO(personModified)).thenReturn(person);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(PERSON_ENTITY_ID));
        when(personRepository.save(personModified)).thenReturn(personModified);


        PersonDTO sut = personService.update(PERSON_ENTITY_ID.getId(), person);
        assertThat(sut).isEqualTo(person);
    }

    @Test
    void deletePerson_WithExistingPersonId_ShouldNotThrowException() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(PERSON_ENTITY_ID));
        assertThatCode(() -> personService.delete(PERSON_ENTITY_ID.getId())).doesNotThrowAnyException();
    }
}