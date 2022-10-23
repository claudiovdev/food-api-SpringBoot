package com.food.domain.repositoryes;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

}
