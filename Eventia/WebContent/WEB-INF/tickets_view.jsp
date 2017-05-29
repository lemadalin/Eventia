<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/tickets_view_style.css" rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Prata"
	rel="stylesheet">
<script src="js/main_page_tools.js"></script>
<title>Eventia - My Tickets</title>
</head>
<body>
	<ul id="slide-out" class="side-nav blue darken-2">
		<li><div class="userView">
				<div class="background">
					<img src="images/user_bg_image.jpg">
				</div>
				<a href="#!user"><img class="circle" src="images/user_image.png"></a>
				<a href="#!name"><span class="white-text name"><%=session.getAttribute("username")%></span></a>
			</div></li>
		<li><a href="RedirectToEventsView"
			class="waves-effect white-text"><i class="material-icons">announcement</i>Upcoming
				Events</a></li>
		<li><a href="RedirectToTicketsView"
			class="waves-effect white-text"><i class="material-icons">receipt</i>My
				Tickets</a></li>
		<li><div class="divider"></div></li>
		<li><a href="login.jsp" class="waves-effect white-text"><i
				class="material-icons">power_settings_new</i>Logout</a></li>
	</ul>

	<a href="#" data-activates="slide-out" class="button-collapse"><i
		class="material-icons medium">menu</i></a>
	<br>

	<div id="wrapper">
		<table class="bordered centered responsive-table">
			<thead>
				<tr>
					<th>Event</th>
					<th>Venue</th>
					<th>Date</th>
					<th>Cost</th>
					<th>Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%@ page import="java.util.List"%>
				<%@ page import="java.util.ArrayList"%>
				<%@ page import="java.util.Arrays"%>
				<%@ page import="models.Event"%>
				<%@ page import="models.Ticket"%>
				<%@ page import="models.Customer"%>
				<%@ page import="com.fasterxml.jackson.core.JsonFactory"%>
				<%@ page import="com.fasterxml.jackson.core.JsonParser"%>
				<%@ page import="com.fasterxml.jackson.databind.JsonNode"%>
				<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
				<%@ page import="com.fasterxml.jackson.core.type.TypeReference"%>
				<%@ page import="javax.servlet.RequestDispatcher"%>
				<%@ page import="javax.servlet.ServletException"%>
				<%@ page import="javax.servlet.annotation.WebServlet"%>
				<%@ page import="javax.servlet.http.HttpServlet"%>
				<%@ page import="javax.servlet.http.HttpServletRequest"%>
				<%@ page import="javax.servlet.http.HttpServletResponse"%>
				<%@ page import="javax.servlet.http.HttpSession"%>
				<%@ page import="javax.ws.rs.client.Client"%>
				<%@ page import="javax.ws.rs.client.ClientBuilder"%>
				<%@ page import="javax.ws.rs.client.Entity"%>
				<%@ page import="javax.ws.rs.client.WebTarget"%>
				<%@ page import="javax.ws.rs.core.MediaType"%>
				<%@ page import="javax.ws.rs.core.UriBuilder"%>
				<%@ page import="org.glassfish.jersey.client.ClientConfig"%>
				<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>

				<%
					String user_name = (String) session.getAttribute("username");
					if (user_name == null)
						response.sendRedirect("login.jsp");
					else {
						String json = request.getAttribute("getTicketsJSON").toString();
						ObjectMapper mapper = new ObjectMapper();

						TypeReference<List<Ticket>> mapType = new TypeReference<List<Ticket>>() {
						};
						List<Ticket> jsonToTicketList = mapper.readValue(json, mapType);

						for (int i = 0; i < jsonToTicketList.size(); i++) {
							ClientConfig cfg = new ClientConfig();
							Client client = ClientBuilder.newClient(cfg);
							WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/Eventia").build());
							String getEventJSON = target.path("webapi").path("events").path("findEventId")
									.path(jsonToTicketList.get(i).getEventId() + "").request()
									.accept(MediaType.APPLICATION_JSON).get(String.class);
							ObjectMapper mp = new ObjectMapper();
							Event obj = mp.readValue(getEventJSON, Event.class);
				%>
				<tr>
					<td><%=obj.getEventName()%></td>
					<td><%=obj.getEventVenue()%></td>
					<td><%=obj.getEventDate()%></td>
					<td><%=obj.getEventCost()%></td>
					<td>
						<form action="CancelTicketController" method="post">
							<input type="hidden" name="data"
								value="<%=jsonToTicketList.get(i).getTicketId() + "/" + jsonToTicketList.get(i).getCustomerId()
							+ "/" + jsonToTicketList.get(i).getEventId()%>" />
							<button
								class="waves-effect waves-light btn-floating btn-medium red"
								type="submit" name="action">
								<i class="material-icons right">not_interested</i>
							</button>
						</form>
					</td>
				</tr>
				<%
					}
				%>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>