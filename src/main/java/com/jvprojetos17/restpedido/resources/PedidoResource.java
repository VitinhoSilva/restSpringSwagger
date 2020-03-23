package com.jvprojetos17.restpedido.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.RelationNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvprojetos17.restpedido.models.Pedido;
import com.jvprojetos17.restpedido.repository.PedidoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/rest")
@Api(value="API REST pedido")
@CrossOrigin(origins="*")
public class PedidoResource {
	
	@Autowired
	PedidoRepository produtoRepository;
	
	@GetMapping("/pedido")
	@ApiOperation(value="Retorna uma lista de pedido")
	public List<Pedido> listaProdutos() {
		return produtoRepository.findAll();
	}
	
	@GetMapping("/pedido/{id}")
	@ApiOperation(value="Retorna uma pedido pelo id")
	public ResponseEntity<Pedido> findById(@PathVariable(value="id") Long id)
			throws RelationNotFoundException {
		Pedido pedido = produtoRepository.findById(id)
				.orElseThrow(() -> new RelationNotFoundException("Pedido de id " + id + " não existe!"));
		return ResponseEntity.ok().body(pedido);
	}
	
	@PostMapping("/pedido")
	@ApiOperation(value="Inseri um pedido")
	public Pedido salvaProduto(@RequestBody Pedido pedido) {
		return produtoRepository.save(pedido);
	}
	
	
	@DeleteMapping("/pedido/{id}")
	@ApiOperation(value="Deleta um pedido pelo id")
	public Map<String, Boolean> deletaPedido(@PathVariable(value="id") Long id)
			throws RelationNotFoundException {
		Pedido pedido = produtoRepository.findById(id)
				.orElseThrow(() -> new RelationNotFoundException("Pedido de id " + id + " não existe!"));

		produtoRepository.delete(pedido);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PutMapping("/pedido/{id}")
	@ApiOperation(value="Atualiza um pedido")
	public ResponseEntity<Pedido> atualizaProduto(@PathVariable(value="id") Long id,
			@Valid @RequestBody Pedido pedidoReq) throws RelationNotFoundException {
		Pedido pedido = produtoRepository.findById(id)
				.orElseThrow(() -> new RelationNotFoundException("Pedido de id " + id + " não existe!"));

		pedido.setId(pedidoReq.getId());
		pedido.setProduto(pedidoReq.getProduto());
		pedido.setQuantidade(pedidoReq.getQuantidade());
		final Pedido pedidoAtualizado = produtoRepository.save(pedido);
			
		return ResponseEntity.ok(pedidoAtualizado);
	}	
	
}
