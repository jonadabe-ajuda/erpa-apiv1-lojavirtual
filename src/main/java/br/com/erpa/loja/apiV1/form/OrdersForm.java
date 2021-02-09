package br.com.erpa.loja.apiV1.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;



public class OrdersForm {
	
	private Long number;
	
	@NonNull
	@NotEmpty
	@Length(min = 5,max = 100,message = "OrdersForm field description between 5 and 100")
	private String description;
	
	@NotNull
	@NotEmpty
	@Length(min = 11,max = 11,message = "OrdersForm field customersCPF equal 11")
	private String customersCPF;
	
	@NotNull
	@NotEmpty
	@Length(min = 5,max = 100,message = "OrdersForm field customersName between 5 and 100")
	private String customersName;
	
	private List<OrdersItemForm> items = new ArrayList<OrdersItemForm>();

	public Long getNumber() {
		return number;
	}

	public String getDescription() {
		return description;
	}

	public String getCustomersCPF() {
		return customersCPF;
	}

	public String getCustomersName() {
		return customersName;
	}

	public List<OrdersItemForm> getItems() {
		return items;
	}
	
	
	
	

}
