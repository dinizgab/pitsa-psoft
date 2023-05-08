package com.ufcg.psoft.pitsA.repository;

import com.ufcg.psoft.pitsA.model.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long> {

    Sabor findByName(String name);
}
