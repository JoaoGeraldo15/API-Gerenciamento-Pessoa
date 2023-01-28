package com.api.pessoa.domain.service.impl;

import com.api.pessoa.domain.model.Address;
import com.api.pessoa.domain.model.dto.AddressDTO;
import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.model.mapper.AddressMapper;
import com.api.pessoa.domain.repository.AddressRepository;
import com.api.pessoa.domain.service.AddressService;
import com.api.pessoa.domain.service.PersonService;
import com.api.pessoa.domain.service.exeception.EntityNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
            validateMainAddress(addressDTO.getPersonId());
        }

        Address addressSaved = addressRepository.save(addressMapper.toEntity(addressDTO));
        return addressMapper.toDTO(addressSaved);
    }

    @Override
    public List<AddressDTO> listPersonAddress(Long personId) {
        return addressMapper.toDTO(addressRepository.findAllByPersonId(personId));
    }

    @Override
    public AddressDTO updateMainAddress(Long addressId, Long personId) {

        Address newMainAddress = addressRepository.findByPerson_IdAndId(personId, addressId)
                .orElseThrow(() -> new EntityNotFound(String.format("Address with id %d not found ", addressId)));

        validateMainAddress(personId);

        newMainAddress.setIsMainAddress(Boolean.TRUE);
        return addressMapper.toDTO(addressRepository.save(newMainAddress));
    }


    private void validateMainAddress(Long personId) {
        Optional<Address> mainAddress = addressRepository.findByPerson_IdAndAndIsMainAddress(personId, Boolean.TRUE);
        if (mainAddress.isPresent()) {
            Address oldAddress = mainAddress.get();
            oldAddress.setIsMainAddress(Boolean.FALSE);
            addressRepository.save(oldAddress);
        }
    }
}
