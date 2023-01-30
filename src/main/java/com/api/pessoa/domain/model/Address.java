package com.api.pessoa.domain.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Builder
@Table(name = "endereco")
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Address City is required and can't be empty")
    @Column(nullable = false)
    private String city;

    @NotEmpty(message = "Address Street is required and can't be empty")
    @Column(nullable = false)
    private String street;

    @NotEmpty(message = "Address Zipcode is required and can't be empty")
    @Column(nullable = false)
    private String zipcode;

    @NotEmpty(message = "Address Number is required and can't be empty")
    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Boolean isMainAddress = Boolean.FALSE;

    public Address(String city, String street, String zipcode, String number, Boolean isMainAddress) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.number = number;
        this.isMainAddress = isMainAddress;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
