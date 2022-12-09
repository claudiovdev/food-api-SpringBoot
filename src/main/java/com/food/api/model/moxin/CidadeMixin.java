package com.food.api.model.moxin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.core.validation.Groups;
import com.food.domain.model.Estado;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

public class CidadeMixin {
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
