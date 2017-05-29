package services;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Customer;

@Path("/customers")
public class CustomerServices {
	ObjectMapper objectMapper = new ObjectMapper();

	@GET
	@Path("/list_customers")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getCustomers() {
		List<Customer> customerList = DBManager.getInstance().getCustomerList();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(customerList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/findCustomerId/{customerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findCustomer(@PathParam("customerId") int id) {
		Customer customer = DBManager.getInstance().findCustomerById(id);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(customer);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/findCustomerName/{customerName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findCustomer(@PathParam("customerName") String name) {
		Customer customer = DBManager.getInstance().findCustomerByName(name);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(customer);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String addCustomer(String customer) {
		Customer customerObj = new Customer();
		try {
			customerObj = objectMapper.readValue(customer, Customer.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Customer addedCustomer = DBManager.getInstance().insertCustomer(customerObj);
		String result = null;
		if (addedCustomer != null) {
			try {
				result = objectMapper.writeValueAsString(addedCustomer);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@DELETE
	@Path("/delete/{customerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public boolean deleteCustomer(@PathParam("customerId") int id) {
		return DBManager.getInstance().deleteCustomerById(id);
	}
}
