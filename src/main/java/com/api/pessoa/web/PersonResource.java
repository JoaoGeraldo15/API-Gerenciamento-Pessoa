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
        PersonDTO personCreated = personService.save(personDTO);
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

    @PutMapping("{idPerson}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long idPerson, @RequestBody @Valid PersonDTO personDTO) {
        return ResponseEntity.ok(personService.update(idPerson, personDTO));
    }

    @DeleteMapping("{idPerson}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long idPerson) {
        personService.delete(idPerson);
        return ResponseEntity.noContent().build();
    }

}
