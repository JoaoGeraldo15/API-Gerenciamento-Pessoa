package com.api.pessoa.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "person")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required and can't be empty")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "birthday is required and can't be null")
    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "person")
    private List<Address> address = new ArrayList<>();

    public Person(String name, LocalDate birthDay, ArrayList<Address> address) {
        this.name = name;
        this.birthDate = birthDay;
        this.address = address;
    }
}
