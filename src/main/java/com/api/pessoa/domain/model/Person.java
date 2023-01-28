package com.api.pessoa.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "person")
@EqualsAndHashCode(of = {"id"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required and can't be empty")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "birthday is required and can't be null")
    @Column(nullable = false)
    private Date birthDate;

    @OneToMany(mappedBy = "person")
    private List<Address> address = new ArrayList<>();
}
