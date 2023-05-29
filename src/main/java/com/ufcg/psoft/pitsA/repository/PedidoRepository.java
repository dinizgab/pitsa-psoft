package com.ufcg.psoft.pitsA.repository;

import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
