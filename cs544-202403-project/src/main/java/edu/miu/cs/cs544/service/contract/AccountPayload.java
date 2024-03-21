package edu.miu.cs.cs544.service.contract;

import java.io.Serializable;

import edu.miu.cs.cs544.domain.AccountType;
import lombok.Data;

@Data
public class AccountPayload implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private double defaultValue = 10000;
	private double currentValue;
	private AccountType type;

	public AccountPayload(String name, String description, double defaultValue, double currentValue, AccountType type) {
		this.name = name;
		this.description = description;
		this.defaultValue = defaultValue;
		this.currentValue = currentValue;
		this.type = type;
	}
}
