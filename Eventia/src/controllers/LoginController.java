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

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/Eventia").build());

		String getCustomersJSON = target.path("webapi").path("customers").path("list_customers").request()
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		String[] arrJSON = getCustomersJSON.split("\\$\\{([^}]*)\\}");
		boolean flag = false;
		for (String aux : arrJSON) {
			if (aux.toLowerCase().contains(username.toLowerCase())
					&& aux.toLowerCase().contains(password.toLowerCase())) {
				flag = true;
				break;
			}
		}
		if (flag == true) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);

			String getEventsJSON = target.path("webapi").path("events").path("list_events").request()
					.accept(MediaType.APPLICATION_JSON).get(String.class);

			request.setAttribute("getEventsJSON", getEventsJSON);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/events_view.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			response.sendRedirect("login.jsp");
		}
	}
}
