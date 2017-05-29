package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Customer;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("username_reg");
		String pass = request.getParameter("password_reg");

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/Eventia").build());

		Customer customerToAdd = new Customer();
		customerToAdd.setCustomerName(user);
		customerToAdd.setCustomerPassword(pass);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(customerToAdd);
		System.out.println(jsonInString);
		String aux = target.path("webapi").path("customers").path("add").request(MediaType.APPLICATION_JSON)
				.post(Entity.json(jsonInString), String.class);

		response.sendRedirect("login.jsp");
		return;
	}
}
