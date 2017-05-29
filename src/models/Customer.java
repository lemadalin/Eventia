package models;

public class Customer {
	private int customerId;
	private String customerName;
	private String customerPassword;

	public Customer() {
	}

	public Customer(int customerId, String customerName, String customerPassword) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPassword = customerPassword;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [customerId=").append(customerId).append(", customerName=").append(customerName)
				.append(", customerPassword=").append(customerPassword).append("]");
		return builder.toString();
	}
}