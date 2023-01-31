package com.api.pessoa.web;

import com.api.pessoa.domain.model.dto.PersonDTO;
import com.api.pessoa.domain.service.PersonService;
import com.api.pessoa.domain.service.exeception.EntityNotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

import static com.api.pessoa.domain.commom.PersonConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonResource.class)
class PersonResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    void createPerson_WithValidData_ReturnsCreated() throws Exception {
        when(personService.save(PERSON)).thenReturn(PERSON_ID);

        String response = mockMvc.perform(post(URL_PERSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PERSON))
        ).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        PersonDTO sut = objectMapper.readValue(response, PersonDTO.class);
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PERSON_ID);

    }

    @Test
    public void createPlanet_WithInvalidData_ShouldReturnsBadRequest() throws Exception {
        PersonDTO invalidPerson = PersonDTO.builder().name(Strings.EMPTY).address(new ArrayList<>()).birthDate(null).build();
        PersonDTO emptyPerson = new PersonDTO();

        mockMvc.perform(post(URL_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPerson)))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post(URL_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyPerson)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void listPerson_ReturnsPersons() throws Exception {
        when(personService.list()).thenReturn(PERSONS_ID);

        String contentAsString = mockMvc.perform(get(URL_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PersonDTO[] sut = objectMapper.readValue(contentAsString, PersonDTO[].class);
        assertThat(sut).hasSize(1);
        assertThat(sut[0]).isEqualTo(PERSON_ID);
    }

    @Test
    void getPersonById_WithExistingPersonId_ReturnsPerson() throws Exception {
        when(personService.getPersonById(anyLong())).thenReturn(PERSON_ID);

        String contentAsString = mockMvc.perform(get(URL_PERSON + "/" + PERSON_ID.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PersonDTO sut = objectMapper.readValue(contentAsString, PersonDTO.class);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PERSON_ID);
    }

    @Test
    void getPersonById_WithNonExistingPersonId_ShouldReturnNotFound() throws Exception {
        when(personService.getPersonById(anyLong())).thenThrow(EntityNotFound.class);

        mockMvc.perform(get(URL_PERSON + "/" + PERSON_ID.getId())).andExpect(status().isNotFound());
    }

    @Test
    void updatePerson_WithExistingPersonId_ShouldReturnPerson()  throws Exception{
        when(personService.update(1L, PERSON)).thenReturn(PERSON_ID);

        String contentAsString = mockMvc.perform(put(URL_PERSON + "/" + PERSON_ID.getId())
                        .content(objectMapper.writeValueAsString(PERSON))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        PersonDTO sut = objectMapper.readValue(contentAsString, PersonDTO.class);
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PERSON_ID);

    }

    @Test
    void updatePerson_WithNonExistingPersonId_ShouldReturnNotFound() throws Exception {
        when(personService.update(1L, PERSON)).thenThrow(EntityNotFound.class);

        mockMvc.perform(put(URL_PERSON + "/" + PERSON_ID.getId())
                .content(objectMapper.writeValueAsString(PERSON))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void deletePerson_WithExistingPersonId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete(URL_PERSON + "/" + 1L)).andExpect(status().isNoContent());
    }
}