package com.api.pessoa.domain.service;

import com.api.pessoa.domain.model.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    /**
     * Cadastra um endereço (Address) de acordo com os dados de AddressDTO, caso não seja informado o id da PersonDTO ou
     * o id informado não tenha sido cadastrado é lançado uma exception
     * @param addressDTO
     * @return addressDTO
     */
    public AddressDTO save(AddressDTO addressDTO);

    /**
     * Lista os endereços de uma pessoa de acordo com o id informado
     * @param personId
     * @return List<AddressDTO>
     */
    List<AddressDTO> listPersonAddress(Long personId);
}
