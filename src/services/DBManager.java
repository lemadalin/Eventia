package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Customer;
import models.Event;
import models.Ticket;

public class DBManager {
	private static final String URL = "jdbc:mysql://localhost/eventiadb";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "12345";
	private static final DBManager instance = new DBManager();
	private Connection conn;

	public static DBManager getInstance() {
		return instance;
	}

	private DBManager() {
		System.out.println("Loading driver...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// <===============Customer methods===============>
	public List<Customer> getCustomerList() {
		try (Statement st = conn.createStatement()) {
			List<Customer> customerList = new ArrayList<Customer>();
			st.execute("SELECT * FROM customers");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"),
						rs.getString("customer_password"));
				customerList.add(customer);
			}
			// st.close();
			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Customer findCustomerById(int id) {
		try (Statement st = conn.createStatement()) {
			Customer customer = new Customer();
			st.execute("SELECT * FROM customers WHERE customer_id = '" + id + "'");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"),
						rs.getString("customer_password"));
			}
			return customer;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Customer findCustomerByName(String name) {
		try (Statement st = conn.createStatement()) {
			Customer customer = new Customer();
			st.execute("SELECT * FROM customers WHERE customer_name = '" + name + "'");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"),
						rs.getString("customer_password"));
			}
			return customer;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Customer insertCustomer(Customer toAdd) {
		try (Statement st = conn.createStatement()) {
			if (findCustomerByName(toAdd.getCustomerName()).getCustomerId() != 0) {
				return null;
			} else {
				String sql = "INSERT INTO customers(customer_name, customer_password) " + "Values('"
						+ toAdd.getCustomerName() + "','" + toAdd.getCustomerPassword() + "')";
				st.executeUpdate(sql);
				toAdd = findCustomerByName(toAdd.getCustomerName());
				return toAdd;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteCustomerById(int id) {
		try (Statement st = conn.createStatement()) {
			Customer customer = findCustomerById(id);
			if (customer == null)
				return false;
			else {
				st.execute("DELETE FROM customers WHERE customer_id = '" + id + "'");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// <===============Event methods===============>
	public List<Event> getEventList() {
		try (Statement st = conn.createStatement()) {
			List<Event> eventList = new ArrayList<Event>();
			st.execute("SELECT * FROM events");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				Event event = new Event(rs.getInt("event_id"), rs.getString("event_name"), rs.getString("event_venue"),
						rs.getTimestamp("event_date"), rs.getDouble("event_cost"));
				eventList.add(event);
			}
			return eventList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Event findEventById(int id) {
		try (Statement st = conn.createStatement()) {
			Event event = new Event();
			st.execute("SELECT * FROM events WHERE event_id = '" + id + "'");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				event = new Event(rs.getInt("event_id"), rs.getString("event_name"), rs.getString("event_venue"),
						rs.getTimestamp("event_date"), rs.getDouble("event_cost"));
			}
			return event;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Event findEventByName(String name) {
		try (Statement st = conn.createStatement()) {
			Event event = new Event();
			st.execute("SELECT * FROM events WHERE event_name = '" + name + "'");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				event = new Event(rs.getInt("event_id"), rs.getString("event_name"), rs.getString("event_venue"),
						rs.getTimestamp("event_date"), rs.getDouble("event_cost"));
			}
			return event;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// <===============Ticket methods===============>
	public List<Ticket> getTicketListById(int id) {
		try (Statement st = conn.createStatement()) {
			List<Ticket> ticketList = new ArrayList<Ticket>();
			st.execute("SELECT * FROM tickets WHERE customer_id = " + id);
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				Ticket ticket = new Ticket(rs.getInt("ticket_id"), rs.getInt("customer_id"), rs.getInt("event_id"));
				ticketList.add(ticket);
			}
			// st.close();
			return ticketList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Ticket findTicketById(int id) {
		try (Statement st = conn.createStatement()) {
			Ticket ticket = new Ticket();
			st.execute("SELECT * FROM tickets WHERE ticket_id = '" + id + "'");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				ticket = new Ticket(rs.getInt("ticket_id"), rs.getInt("customer_id"), rs.getInt("event_id"));
			}
			return ticket;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Ticket insertTicket(Ticket toAdd) {
		try (Statement st = conn.createStatement()) {
			String sql = "INSERT INTO tickets(customer_id, event_id) " + "Values('" + toAdd.getCustomerId() + "','"
					+ toAdd.getEventId() + "')";
			st.executeUpdate(sql);
			toAdd = findTicketById(toAdd.getTicketId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toAdd;
	}
	
	public boolean deleteTicketById(int id) {
		try (Statement st = conn.createStatement()) {
			Ticket ticket = findTicketById(id);
			if (ticket == null)
				return false;
			else {
				st.execute("DELETE FROM tickets WHERE ticket_id = '" + id + "'");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}