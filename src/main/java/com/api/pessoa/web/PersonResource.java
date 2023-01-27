package com.api.pessoa.web;

import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonResource {
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO personDTO) {
        PersonDTO personCreated = personService.create(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> list() {
        List<PersonDTO> personsList = personService.list();
        return ResponseEntity.status(HttpStatus.OK).body(personsList);
    }

    @GetMapping("/{idPerson}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long idPerson) {
        return ResponseEntity.ok(personService.getPersonById(idPerson));
    }

}
