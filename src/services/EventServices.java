package services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Event;

@Path("/events")
public class EventServices {
	ObjectMapper objectMapper = new ObjectMapper();

	@GET
	@Path("/list_events")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getEvents() {
		List<Event> eventList = DBManager.getInstance().getEventList();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(eventList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/findEventId/{EventId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findEvent(@PathParam("EventId") int id) {
		Event event = DBManager.getInstance().findEventById(id);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@GET
	@Path("/findEventName/{EventName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findEvent(@PathParam("EventName") String name) {
		Event event = DBManager.getInstance().findEventByName(name);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
