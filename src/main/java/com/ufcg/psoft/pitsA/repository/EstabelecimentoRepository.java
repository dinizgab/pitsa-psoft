package com.ufcg.psoft.pitsA.repository;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
}
