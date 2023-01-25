package com.api.pessoa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "endereco")
@EqualsAndHashCode(of = {"id"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String city;

    @NotEmpty
    @Column(nullable = false)
    private String street;

    @NotEmpty
    @Column(nullable = false)
    private String zipcode;

    @NotEmpty
    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Boolean isMainAddress = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;
}
