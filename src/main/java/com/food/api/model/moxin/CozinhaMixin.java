package com.food.api.model.moxin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.domain.model.Restaurante;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
