package com.api.pessoa.web;

import com.api.pessoa.domain.model.dto.AddressDTO;
import com.api.pessoa.domain.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/address")
public class AddressResource {

    private AddressService addressService;

    /**
     * Endpoint para cadastrar um endereço (Address) de acordo com os dados do corpo da requisição (AddressDTO), caso
     * não seja informado o id da PersonDTO ou o id informado não tenha sido cadastrado é lançado uma exception e o
     * status code 400 BadRequest.
     * @param addressDTO
     * @return addressDTO
     */
    @PostMapping
    public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(addressDTO));
    }
}
