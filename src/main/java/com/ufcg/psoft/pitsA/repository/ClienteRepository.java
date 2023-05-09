package com.ufcg.psoft.pitsA.repository;

import com.ufcg.psoft.pitsA.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
