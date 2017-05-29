<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/events_view_style.css" rel="stylesheet" type="text/css">
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
<title>Eventia - Upcoming Events</title>
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
		<li><a href="RedirectToEventsView" class="waves-effect white-text"><i
				class="material-icons">announcement</i>Upcoming Events</a></li>
		<li><a href="RedirectToTicketsView" class="waves-effect white-text"><i
				class="material-icons">receipt</i>My Tickets</a></li>
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
					<th>Buy</th>
				</tr>
			</thead>
			<tbody>
				<%@ page import="java.util.List"%>
				<%@ page import="java.util.ArrayList"%>
				<%@ page import="java.util.Arrays"%>
				<%@ page import="models.Event"%>
				<%@ page import="com.fasterxml.jackson.core.JsonFactory"%>
				<%@ page import="com.fasterxml.jackson.core.JsonParser"%>
				<%@ page import="com.fasterxml.jackson.databind.JsonNode"%>
				<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
				<%@ page import="com.fasterxml.jackson.core.type.TypeReference"%>

				<%
					String user_name = (String) session.getAttribute("username");
					if (user_name == null)
						response.sendRedirect("login.jsp");
					else {
						String json = request.getAttribute("getEventsJSON").toString();
						ObjectMapper mapper = new ObjectMapper();

						TypeReference<List<Event>> mapType = new TypeReference<List<Event>>() {
						};
						List<Event> jsonToEventList = mapper.readValue(json, mapType);

						for (int i = 0; i < jsonToEventList.size(); i++) {
				%>
				<tr>
					<td><%=jsonToEventList.get(i).getEventName()%></td>
					<td><%=jsonToEventList.get(i).getEventVenue()%></td>
					<td><%=jsonToEventList.get(i).getEventDate()%></td>
					<td><%="$" + jsonToEventList.get(i).getEventCost()%></td>
					<td>
						<form action="BuyTicketController" method="post">
							<input type="hidden" name="data"
								value="<%=user_name+"/"+jsonToEventList.get(i).getEventId()%>" />
							<button
								class="waves-effect waves-light btn-floating btn-medium green"
								type="submit" name="action">
								<i class="material-icons right">today</i>
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