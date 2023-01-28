package com.api.pessoa.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "endereco")
@EqualsAndHashCode(of = {"id"})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
