package com.api.pessoa.domain.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NotEmpty(message = "Name is required and can't be empty")
    private String name;

    @NotNull(message = "birthday is required and can't be null")
    private Date birthDate;

    private List<AddressDTO> address = new ArrayList<>();

}
