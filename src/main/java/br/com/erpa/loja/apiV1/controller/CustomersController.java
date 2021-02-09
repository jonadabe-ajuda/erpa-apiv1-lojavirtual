package br.com.erpa.loja.apiV1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.erpa.application.command.DeleteOrders;
import br.com.erpa.application.command.RecoverOrdersAll;
import br.com.erpa.application.command.RecoverOrdersNumber;
import br.com.erpa.application.command.SaveOrders;
import br.com.erpa.application.command.UpdateOrders;
import br.com.erpa.application.dto.OrdersDTO;
import br.com.erpa.infra.RepositoryOrdersMysql;
import br.com.erpa.loja.apiV1.form.OrdersForm;

@RestController	
@RequestMapping("/orders")
public class CustomersController {
	
	
	@PostMapping
	public ResponseEntity<OrdersDTO> insert(@RequestBody @Valid OrdersForm ordersForm, UriComponentsBuilder urBuilder) {
		
		
		
		SaveOrders saveOrders = new SaveOrders(new RepositoryOrdersMysql("erpa-loja-ec2"));
		OrdersDTO ordersDTO = convertToOrderDTO(ordersForm);
		saveOrders.execute(ordersDTO);
		
		URI uri = urBuilder.path("/customers/{number}").buildAndExpand(ordersDTO.getNumber()).toUri();
		return ResponseEntity.created(uri).body(ordersDTO);
		
	}


	
	@GetMapping
	public List<OrdersDTO> all() {
		RecoverOrdersAll recoverCustomerAll = new RecoverOrdersAll(new  RepositoryOrdersMysql("erpa-loja-ec2"));
		return recoverCustomerAll.execute();
	}
	
	@GetMapping("/{number}")
	public ResponseEntity<OrdersDTO> recoverWithNumber(@PathVariable(required = true) Long number) {
		
		RecoverOrdersNumber recoverOrdersNumber = new RecoverOrdersNumber(new RepositoryOrdersMysql("erpa-loja-ec2"));
		OrdersDTO ordersDTO = recoverOrdersNumber.execute(number);
		
		if (ordersDTO != null) {
			return ResponseEntity.ok(ordersDTO);
		}
	
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{number}")
	public ResponseEntity<OrdersDTO> update(@RequestBody @Valid OrdersForm customersUpdateForm, @PathVariable(required = true) String cpf, UriComponentsBuilder urBuilder) {
		
		UpdateOrders updateOrders = new UpdateOrders(new RepositoryOrdersMysql("erpa-loja-ec2"));
		OrdersDTO ordersDTO = convertToOrderDTO(customersUpdateForm);
		updateOrders.execute(ordersDTO);
		
		
		URI uri = urBuilder.path("/customers/{cpf}").buildAndExpand(ordersDTO.getNumber()).toUri();
		return ResponseEntity.created(uri).body(ordersDTO);
		
	}
	
	@DeleteMapping("/{number}")
	public ResponseEntity<?> delete(@PathVariable(required = true) Long number) {
	
		DeleteOrders deleteOrders = new DeleteOrders(new RepositoryOrdersMysql("erpa-loja-ec2"));
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setNumber(number);
		deleteOrders.execute(ordersDTO);
		
		return ResponseEntity.ok(ordersDTO);
		
	}
		
	private OrdersDTO convertToOrderDTO(OrdersForm ordersForm) {
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setNumber(ordersForm.getNumber());
		ordersDTO.setDescription(ordersForm.getDescription());
		ordersDTO.setCustomersCPF(ordersForm.getCustomersCPF());
		ordersDTO.setCustomersName(ordersForm.getCustomersName());
		ordersForm.getItems().forEach( item -> {
			ordersDTO.addItems(item.getProductCode(), item.getProductName(), 
							   item.getAmount(), item.getValue());
		});
		return ordersDTO;
	}	
	
	

}
