package com.api.pessoa.domain.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;

    public AddressDTO(String city, String street, String zipcode, String number, Boolean isMainAddress) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.number = number;
        this.isMainAddress = isMainAddress;
    }

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
