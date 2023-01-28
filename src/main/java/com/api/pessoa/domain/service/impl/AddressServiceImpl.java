package com.api.pessoa.domain.service.impl;

import com.api.pessoa.domain.model.Address;
import com.api.pessoa.domain.model.dto.AddressDTO;
import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.model.mapper.AddressMapper;
import com.api.pessoa.domain.repository.AddressRepository;
import com.api.pessoa.domain.service.AddressService;
import com.api.pessoa.domain.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    private AddressMapper addressMapper;

    private PersonService personService;

    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        PersonDTO personDTO = personService.getPersonById(addressDTO.getPersonId());
        addressDTO.setPersonId(personDTO.getId());
        if (addressDTO.getIsMainAddress()) {
            validateMainAddress(addressDTO);
        }

        Address addressSaved = addressRepository.save(addressMapper.toEntity(addressDTO));
        return addressMapper.toDTO(addressSaved);
    }

    private void validateMainAddress(AddressDTO addressDTO) {
        Optional<Address> mainAddress = addressRepository.findByPerson_IdAndAndIsMainAddress(addressDTO.getPersonId(), Boolean.TRUE);
        if (mainAddress.isPresent()) {
            Address oldAddress = mainAddress.get();
            oldAddress.setIsMainAddress(Boolean.FALSE);
            addressRepository.save(oldAddress);
        }
    }
}
