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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Customer;

@WebServlet("/RedirectToTicketsView")
public class RedirectToTicketsView extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String user_name = (String) session.getAttribute("username");

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/Eventia").build());

		String getCustomerId = target.path("webapi").path("customers").path("findCustomerName").path(user_name)
				.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Customer obj = mapper.readValue(getCustomerId, Customer.class);
		System.out.println("customer_id=" + obj.getCustomerId());

		String getTicketsJSON = target.path("webapi").path("tickets").path("ticket_details")
				.path(obj.getCustomerId() + "").request().accept(MediaType.APPLICATION_JSON).get(String.class);

		request.setAttribute("getTicketsJSON", getTicketsJSON);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/tickets_view.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
