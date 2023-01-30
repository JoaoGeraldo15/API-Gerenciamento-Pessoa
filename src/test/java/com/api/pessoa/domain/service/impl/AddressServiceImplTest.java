package com.api.pessoa.domain.service.impl;

import com.api.pessoa.domain.model.Address;
import com.api.pessoa.domain.model.Person;
import com.api.pessoa.domain.model.dto.AddressDTO;
import com.api.pessoa.domain.model.mapper.AddressMapper;
import com.api.pessoa.domain.repository.AddressRepository;
import com.api.pessoa.domain.service.PersonService;
import com.api.pessoa.domain.service.exeception.EntityNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.api.pessoa.domain.commom.AddressConstants.*;
import static com.api.pessoa.domain.commom.PersonConstants.PERSON_ENTITY_ID;
import static com.api.pessoa.domain.commom.PersonConstants.PERSON_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private PersonService personService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @Test
    void createAddress_WithExistingPersonIdAndNonMainAddress_ShouldReturnAddress() {
        AddressDTO addressPersonId = ADDRESS;
        addressPersonId.setPersonId(1L);

        Address addressEntityPersonId = ADDRESS_ENTITY;
        addressEntityPersonId.setPerson(new Person(1L, null, null, null));

        when(personService.getPersonById(anyLong())).thenReturn(PERSON_ID);
        when(addressMapper.toEntity(addressPersonId)).thenReturn(addressEntityPersonId);
        when(addressMapper.toDTO(ADDRESS_ENTITY_ID)).thenReturn(ADDRESS_ID);
        when(addressRepository.save(addressEntityPersonId)).thenReturn(ADDRESS_ENTITY_ID);

        AddressDTO sut = addressService.save(addressPersonId);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(ADDRESS_ID);
    }

    @Test
    void createAddress_WithExistingPersonIdAndFirstMainAddress_ShouldReturnAddress() {
        AddressDTO addressPersonId = AddressDTO.builder()
                .isMainAddress(Boolean.TRUE)
                .city("City Land")
                .street("Street for")
                .zipcode("6489233")
                .number("259")
                .personId(1L)
                .build();

        Address addressEntityPersonId = ADDRESS_ENTITY;
        addressEntityPersonId.setPerson(new Person(1L, null, null, null));
        addressEntityPersonId.setIsMainAddress(Boolean.TRUE);

        Address addressEntityIdMainAddress = ADDRESS_ENTITY_ID;
        addressEntityIdMainAddress.setIsMainAddress(Boolean.TRUE);

        when(personService.getPersonById(anyLong())).thenReturn(PERSON_ID);
        when(addressRepository.findByPerson_IdAndAndIsMainAddress(addressPersonId.getPersonId(), Boolean.TRUE)).thenReturn(Optional.empty());

        when(addressMapper.toEntity(addressPersonId)).thenReturn(addressEntityPersonId);
        when(addressRepository.save(addressEntityPersonId)).thenReturn(addressEntityIdMainAddress);
        when(addressMapper.toDTO(addressEntityIdMainAddress)).thenReturn(addressPersonId);

        AddressDTO sut = addressService.save(addressPersonId);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(addressPersonId);
    }

    @Test
    void createAddress_WithExistingPersonIdAndSecondMainAddress_ShouldReturnAddress() {
        Address firstAddressPersonId = Address.builder()
                .isMainAddress(Boolean.TRUE)
                .city("First Land city")
                .street("Street one")
                .zipcode("654321")
                .number("259")
                .person(PERSON_ENTITY_ID)
                .build();

        AddressDTO addressPersonId = AddressDTO.builder()
                .isMainAddress(Boolean.TRUE)
                .city("City Land")
                .street("Street for")
                .zipcode("6489233")
                .number("259")
                .personId(1L)
                .build();

        Address addressEntityPersonId = ADDRESS_ENTITY;
        addressEntityPersonId.setPerson(new Person(1L, null, null, null));
        addressEntityPersonId.setIsMainAddress(Boolean.TRUE);

        Address addressEntityIdMainAddress = ADDRESS_ENTITY_ID;
        addressEntityIdMainAddress.setIsMainAddress(Boolean.TRUE);

        when(personService.getPersonById(anyLong())).thenReturn(PERSON_ID);
        when(addressRepository.findByPerson_IdAndAndIsMainAddress(addressPersonId.getPersonId(), Boolean.TRUE)).thenReturn(Optional.of(firstAddressPersonId));

        when(addressMapper.toEntity(addressPersonId)).thenReturn(addressEntityPersonId);
        when(addressRepository.save(addressEntityPersonId)).thenReturn(addressEntityIdMainAddress);
        when(addressMapper.toDTO(addressEntityIdMainAddress)).thenReturn(addressPersonId);

        AddressDTO sut = addressService.save(addressPersonId);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(addressPersonId);
    }

    @Test
    void listPersonAddress_ShouldReturnAddresses() {
        when(addressMapper.toDTO(ADDRESSES_ENTITY)).thenReturn(Arrays.asList(ADDRESS_ID));
        when(addressRepository.findAllByPersonId(anyLong())).thenReturn(ADDRESSES_ENTITY);

        List<AddressDTO> sut = addressService.listPersonAddress(1L);
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(ADDRESS_ID);
    }

    @Test
    void updateMainAddress_WithNonExistingAddressId_ShouldThrowEntityNotFound() {
        when(addressRepository.findByPerson_IdAndId(anyLong(), anyLong())).thenThrow(EntityNotFound.class);

        assertThatThrownBy(() -> addressService.updateMainAddress(1L, 1L)).isInstanceOf(EntityNotFound.class);
    }

    @Test
    void updateMainAddress_WithExistingAddressId_ShouldReturnMainAddress() {
        when(addressRepository.findByPerson_IdAndId(anyLong(), anyLong())).thenReturn(Optional.of(ADDRESS_ENTITY_ID));
        when(addressRepository.findByPerson_IdAndAndIsMainAddress(1L, Boolean.TRUE)).thenReturn(Optional.empty());
        AddressDTO addressId = ADDRESS_ID;
        addressId.setIsMainAddress(Boolean.TRUE);

        Address mainAddressEntity = ADDRESS_ENTITY_ID;
        mainAddressEntity.setIsMainAddress(Boolean.TRUE);

        when(addressRepository.save(mainAddressEntity)).thenReturn(mainAddressEntity);
        when(addressMapper.toDTO(mainAddressEntity)).thenReturn(addressId);

        AddressDTO sut = addressService.updateMainAddress(1L, 1L);
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(addressId);

    }
}