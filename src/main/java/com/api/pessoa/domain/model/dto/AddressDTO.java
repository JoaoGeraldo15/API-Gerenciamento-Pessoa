package com.api.pessoa.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;

    @NotEmpty(message = "Address City is required and can't be empty")
    private String city;

    @NotEmpty(message = "Address Street is required and can't be empty")
    private String street;

    @NotEmpty(message = "Address Zipcode is required and can't be empty")
    private String zipcode;

    @NotEmpty(message = "Address Number is required and can't be empty")
    private String number;

    private Boolean isMainAddress = Boolean.FALSE;

    private Long personId;
}
