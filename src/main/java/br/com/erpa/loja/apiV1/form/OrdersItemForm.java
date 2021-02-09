package br.com.erpa.loja.apiV1.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrdersItemForm {
	
	@NotNull
	@NotEmpty
	@Size(min = 3,max = 50,message = "CustomerDTO field name between 2 and 50")
	private String productCode;
	
	
	@NotNull
	@NotEmpty
	@Size(min = 5,max = 100,message = "CustomerDTO field number equal 11")
	private String productName;
	
	@NotNull
	private BigDecimal value;
	
	@NotNull
	private BigDecimal amount;

	public String getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public BigDecimal getValue() {
		return value;
	}

	public BigDecimal getAmount() {
		return amount;
	}	
	
	
	

}
