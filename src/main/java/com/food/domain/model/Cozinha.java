package com.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Cozinha {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nome;
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurates = new ArrayList<>();
}
