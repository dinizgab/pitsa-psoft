package com.ufcg.psoft.pitsA.repository;

import com.ufcg.psoft.pitsA.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}

