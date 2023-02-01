package com.api.pessoa.domain.repository;

import com.api.pessoa.domain.model.Person;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.api.pessoa.domain.commom.PersonConstants.PERSON_ENTITY;
import static com.api.pessoa.domain.commom.PersonConstants.PERSON_ENTITY_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createPerson_WithValidData_ShouldReturnPersonSaved() {
        Person personSaved = personRepository.save(PERSON_ENTITY);
        Person sut = testEntityManager.find(Person.class, personSaved.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PERSON_ENTITY.getName());
        assertThat(sut.getBirthDate()).isEqualTo(PERSON_ENTITY.getBirthDate());
        assertThat(sut.getAddress()).isEqualTo(PERSON_ENTITY.getAddress());
    }

    @Test
    public void createPerson_WithInvalidData_ShouldThrowRunTimeException() {
        Person invalidPerson = Person.builder().name(Strings.EMPTY).birthDate(null).build();

        assertThatThrownBy(() -> personRepository.save(invalidPerson)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @Sql(scripts = "/import_persons.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void listPerson_ReturnsAllPersons() {
        List<Person> persons = personRepository.findAll();

        assertThat(persons).isNotEmpty();
        assertThat(persons).hasSize(15);
        assertThat(persons.get(0)).isEqualTo(PERSON_ENTITY_ID);

    }

    @Test
    public void findById_WithExistingPersonId_ShouldReturnsPerson() {
        Person person = testEntityManager.persistFlushFind(PERSON_ENTITY);

        Optional<Person> sut = personRepository.findById(person.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PERSON_ENTITY_ID);

    }

    @Test
    public void findById_WithNonExistingPersonId_ShouldReturnsEmpty() {

        Optional<Person> sut = personRepository.findById(1L);
        assertThat(sut).isEmpty();

    }

    @Test
    public void updatePerson_ShouldReturnsUpdatedPerson() {
        Person person = testEntityManager.persistFlushFind(PERSON_ENTITY);
        person.setBirthDate(LocalDate.of(2010, 06, 15));

        Person personSaved = personRepository.save(person);
        Person sut = testEntityManager.find(Person.class, personSaved.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getBirthDate()).isEqualTo(person.getBirthDate());


    }

    @Test
    public void removePlanet_WithExistingId_RemovesPlanetFromDatabase() {
        Person sut = testEntityManager.persistFlushFind(PERSON_ENTITY);
        assertThatCode(() -> personRepository.deleteById(sut.getId())).doesNotThrowAnyException();

        Person sutDeleted = testEntityManager.find(Person.class, sut.getId());
        assertThat(sutDeleted).isNull();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        assertThatCode(() -> personRepository.deleteById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }

}