package com.api.pessoa.web;

import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonResource {
    private PersonService personService;

    /**
     * Endpoint para cadastrar uma pessoa no sistema, retorna a pessoa criada e status code 201 CREATED
     * @param personDTO
     * @return ResponseEntity<PersonDTO>
     */
    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO personDTO) {
        PersonDTO personCreated = personService.save(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
    }

    /**
     * Retorna uma lista de PersonDTO e status code 200 OK
     * @return ResponseEntity<List<PersonDTO>>
     */
    @GetMapping
    public ResponseEntity<List<PersonDTO>> list() {
        List<PersonDTO> personsList = personService.list();
        return ResponseEntity.status(HttpStatus.OK).body(personsList);
    }

    /**
     * Retorna uma PersonDTO de acordo com o id informado, caso exista PersonDTO com esse id informado o status code
     * será 200 OK, e se não for encontrado é lançado a exception EntityNotFound e status code será 404 NotFound
     * @param idPerson
     * @return ResponseEntity<PersonDTO>
     */
    @GetMapping("/{idPerson}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long idPerson) {
        return ResponseEntity.ok(personService.getPersonById(idPerson));
    }

    /**
     * Atualiza uma Person de acordo com o PersonDTO informado. Caso não exista é lançado a exception EntityNotFound e o
     * status code será 404 NotFound, caso Person com o id informado exista, será atualizado a entidade e o status code
     * será 200 OK
     * @param idPerson
     * @param personDTO
     * @return ResponseEntity<PersonDTO>
     */
    @PutMapping("{idPerson}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long idPerson, @RequestBody @Valid PersonDTO personDTO) {
        return ResponseEntity.ok(personService.update(idPerson, personDTO));
    }

    /**
     * Deleta Person de acordo com o id informado. Caso exista o status code de resposta será 204 NoContent, mas se
     * existir a exception EntityNotFound será lançada e o status code será 404 NotFound
     * @param idPerson
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("{idPerson}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long idPerson) {
        personService.delete(idPerson);
        return ResponseEntity.noContent().build();
    }

}
