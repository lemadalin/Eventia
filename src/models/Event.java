package models;

import java.util.Date;

public class Event {
	private int eventId;
	private String eventName;
	private String eventVenue;
	private Date eventDate;
	private double eventCost;

	public Event() {
	}

	public Event(int eventId, String eventName, String eventVenue, Date eventDate, double eventCost) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventVenue = eventVenue;
		this.eventDate = eventDate;
		this.eventCost = eventCost;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventVenue() {
		return eventVenue;
	}

	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public double getEventCost() {
		return eventCost;
	}

	public void setEventCost(double eventCost) {
		this.eventCost = eventCost;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [eventId=").append(eventId).append(", eventName=").append(eventName)
				.append(", eventVenue=").append(eventVenue).append(", eventDate=").append(eventDate)
				.append(", eventCost=").append(eventCost).append("]");
		return builder.toString();
	}
}