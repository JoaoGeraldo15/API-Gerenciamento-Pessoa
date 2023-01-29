package com.api.pessoa.domain.commom;

import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.dto.PersonDTO;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonConstants {
    public static final String URL_PERSON = "/api/persons";

    public static final Person PERSON_ENTITY = new Person("João Geraldo", LocalDate.of(1994, 11, 25), new ArrayList<>());
    public static final PersonDTO PERSON = new PersonDTO("João Geraldo", LocalDate.of(1994, 11, 25), new ArrayList<>());
    public static final Person INVALID_PERSON_ENTITY = new Person(Strings.EMPTY, null, null);
    public static final PersonDTO INVALID_PERSON = new PersonDTO(Strings.EMPTY,null, null);

    public static final Person PERSON_ENTITY_ID = new Person(1L, "João Geraldo", LocalDate.of(1994, 11, 25), new ArrayList<>());
    public static final PersonDTO PERSON_ID = new PersonDTO(1L, "João Geraldo", LocalDate.of(1994, 11, 25), new ArrayList<>());

    public static final List<PersonDTO> PERSONS_ID = new ArrayList<PersonDTO>() {{
        add(PERSON_ID);
    }};

    public static final List<Person> PERSONS_ENTITIES_ID = new ArrayList<Person>() {{
        add(PERSON_ENTITY_ID);
    }};
}
