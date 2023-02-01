package com.api.pessoa.web;

import com.api.pessoa.domain.model.dto.AddressDTO;
import com.api.pessoa.domain.service.AddressService;
import com.api.pessoa.domain.service.exeception.EntityNotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static com.api.pessoa.domain.commom.AddressConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressResource.class)
class AddressResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @Test
    void createAddress_WithValidData_ShouldReturnsCreated() throws Exception {
        AddressDTO addressDTO = ADDRESS_ID;
        addressDTO.setId(null);

        when(addressService.save(addressDTO)).thenReturn(ADDRESS_ID);

        String contentAsString = mockMvc.perform(post(URL_ADDRESS).content(objectMapper.writeValueAsString(addressDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AddressDTO sut = objectMapper.readValue(contentAsString, AddressDTO.class);
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(ADDRESS_ID);
    }

    @Test
    void createAddress_WithInvalidData_ShouldReturnsBadRequest() throws Exception {
        mockMvc.perform(post(URL_ADDRESS).content(objectMapper.writeValueAsString(ADDRESS))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void listPersonAddress_WithExistingPersonId_ShouldReturnsOk() throws Exception {

        when(addressService.listPersonAddress(anyLong())).thenReturn(ADDRESSES);

        String URL = String.format(URL_ADDRESS + "/person/" + 1);
        String contentAsString = mockMvc.perform(get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AddressDTO[] addresses = objectMapper.readValue(contentAsString, AddressDTO[].class);
        assertThat(addresses).isNotEmpty();
        assertThat(addresses).hasSize(1);
        assertThat(addresses[0]).isEqualTo(ADDRESS_ID);

    }

    @Test
    void listPersonAddress_WithNonExistingPersonId_ShouldReturnsOkWithEmptyList() throws Exception {

        when(addressService.listPersonAddress(anyLong())).thenReturn(new ArrayList<>());

        String URL = String.format(URL_ADDRESS + "/person/" + 1);
        String contentAsString = mockMvc.perform(get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AddressDTO[] addresses = objectMapper.readValue(contentAsString, AddressDTO[].class);
        assertThat(addresses).isEmpty();

    }

    @Test
    void updateMainAddress_WithExistingAddress_ShouldReturnsOk() throws Exception {
        AddressDTO addressId = ADDRESS_ID;
        addressId.setIsMainAddress(Boolean.TRUE);
        when(addressService.updateMainAddress(anyLong(), anyLong())).thenReturn(addressId);
        String URL = String.format(URL_ADDRESS + "/update-main?addressId=1&personId=1");
        String contentAsString = mockMvc.perform(put(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AddressDTO sut = objectMapper.readValue(contentAsString, AddressDTO.class);
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(ADDRESS_ID);
    }

    @Test
    void updateMainAddress_WithNonExistingAddress_ShouldReturnsBadRequest() throws Exception {
        when(addressService.updateMainAddress(anyLong(), anyLong())).thenThrow(EntityNotFound.class);

        String URL = String.format(URL_ADDRESS + "/update-main?addressId=1&personId=1");
        mockMvc.perform(put(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}