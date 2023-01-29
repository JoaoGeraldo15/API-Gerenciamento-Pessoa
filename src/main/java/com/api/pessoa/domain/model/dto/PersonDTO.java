package com.api.pessoa.domain.model.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PersonDTO {

    public PersonDTO(String name, LocalDate birthDate, List<AddressDTO> address) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

    private Long id;

    @NotEmpty(message = "Name is required and can't be empty")
    private String name;

    @NotNull(message = "birthday is required and can't be null")
    private LocalDate birthDate;

    private List<AddressDTO> address = new ArrayList<>();

}
