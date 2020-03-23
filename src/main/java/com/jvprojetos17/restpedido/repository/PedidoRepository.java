package com.jvprojetos17.restpedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jvprojetos17.restpedido.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	Pedido findById(long id);	
}
