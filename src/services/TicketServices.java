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
import models.Ticket;

@Path("/tickets")
public class TicketServices {
	ObjectMapper objectMapper = new ObjectMapper();

	@POST
	@Path("/buy_ticket")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String addTicket(String ticket) {
		Ticket ticketObj = new Ticket();
		try {
			ticketObj = objectMapper.readValue(ticket, Ticket.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Ticket addedTicket = DBManager.getInstance().insertTicket(ticketObj);
		String result = null;
		if (addedTicket != null) {
			try {
				result = objectMapper.writeValueAsString(addedTicket);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@GET
	@Path("/ticket_details/{customerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getTicketsById(@PathParam("customerId") int id) {
		List<Ticket> ticketList = DBManager.getInstance().getTicketListById(id);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(ticketList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@POST
	@Path("/cancel_ticket")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String cancelTicket(String ticket) {
		Ticket ticketObj = new Ticket();
		try {
			ticketObj = objectMapper.readValue(ticket, Ticket.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		boolean result = DBManager.getInstance().deleteTicketById(ticketObj.getTicketId());
		if (result == true)
			return "Ticket with ID " + ticketObj.getTicketId() + " has been canceled!";
		else
			return "Ticket cancelation didn't work";
	}
}
