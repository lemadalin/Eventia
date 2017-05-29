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
import models.Ticket;

@WebServlet("/BuyTicketController")
public class BuyTicketController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = request.getParameter("data");
		String[] arr = result.split("/");

		System.out.println("customer_name=" + arr[0]);
		System.out.println("eventId=" + arr[1]);

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/Eventia").build());

		String getCustomerId = target.path("webapi").path("customers").path("findCustomerName").path(arr[0]).request()
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Customer obj = mapper.readValue(getCustomerId, Customer.class);
		System.out.println("customer_id=" + obj.getCustomerId());

		Ticket ticket = new Ticket();
		ticket.setCustomerId(obj.getCustomerId());
		ticket.setEventId(Integer.parseInt(arr[1]));

		String jsonTicket = mapper.writeValueAsString(ticket);
		System.out.println(jsonTicket);
		String aux = target.path("webapi").path("tickets").path("buy_ticket").request(MediaType.APPLICATION_JSON)
				.post(Entity.json(jsonTicket), String.class);

		// <<======================RELOAD_PAGE==================================>>

		config = new ClientConfig();
		client = ClientBuilder.newClient(config);
		target = client.target(UriBuilder.fromUri("http://localhost:8080/Eventia").build());
		mapper = new ObjectMapper();
		HttpSession session = request.getSession();
		session.setAttribute("username", arr[0]);

		String getEventsJSON = target.path("webapi").path("events").path("list_events").request()
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		request.setAttribute("getEventsJSON", getEventsJSON);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/events_view.jsp");
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}

		// <<===================================================================>>
	}
}
