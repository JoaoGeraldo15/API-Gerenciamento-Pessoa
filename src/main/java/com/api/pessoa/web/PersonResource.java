package com.api.pessoa.web;

import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonResource {
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDTO> cadastrar(@RequestBody @Valid PersonDTO personDTO) {
        PersonDTO personCreated = personService.create(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
    }

}
