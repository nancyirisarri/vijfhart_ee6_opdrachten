package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<Integer, Person> mapPerson = new HashMap<Integer, Person>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		printHead(out);

		HttpSession session = request.getSession();

		Integer number = new Integer(6);
		session.setAttribute("mycount", number);
		String name = "Nancy";
		session.setAttribute("thename", name);
		session.setAttribute("thelist", mapPerson);

		printForm(out);

		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		HttpSession session = request.getSession();

		PrintWriter out = response.getWriter();

		printHead(out);

		String key = request.getParameter("key");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String buttonPressed = request.getParameter("button");

		try {
			if (buttonPressed.equals("Add") && key != null && firstName != null && lastName != null && !key.isEmpty()
					&& !firstName.isEmpty() && !lastName.isEmpty()) {
				Integer keyInt = Integer.parseInt(key);

				Person person = new Person(key, firstName, lastName);
				mapPerson.put(keyInt, person);
				session.setAttribute("thelist", mapPerson);

			} else if (buttonPressed.equals("Remove") && key != null && !key.isEmpty()) {
				Integer keyInt = Integer.parseInt(key);

				Person p = mapPerson.get(keyInt);

				if (p != null) {
					mapPerson.remove(keyInt);
					session.setAttribute("thelist", mapPerson);
					out.printf("<h3>Removed person with key: %s, first name: %s, last name: %s.</h3>", keyInt, p.getFirstName(), p.getLastName());
				} else if (p == null) {
					out.printf("<h3>No person found with Key: %s.</h3>", keyInt);
				}

			} else {
				out.println("<h3>To Add, please fill in all fields.<br>To Remove, please fill in the Key of a person.</h3>");
			}

		} catch (NumberFormatException e) {
			throw new ServletException("<h3>Please give a number for 'Key'.</h3>");
		}

//		if (key != null && firstName != null && lastName != null && !key.isEmpty() && !firstName.isEmpty()
//				&& !lastName.isEmpty()) {
//
//			try {
//				Integer keyInt = Integer.parseInt(key);
//
//				Person person = new Person(key, firstName, lastName);
//
//				if (buttonPressed.equals("Add")) {
//
//					mapPerson.put(keyInt, person);
//					session.setAttribute("thelist", mapPerson);
//
//				} else if (buttonPressed.equals("Remove")) {
//				}
//			} catch (NumberFormatException e) {
//				throw new ServletException("<h3>Please give a number for 'Key'.</h3>");
//			}
//
//		} else {
//			out.println("<h3>Please fill in all fields.</h3>");
//		}

		printForm(out);

		out.println("</body></html>");
	}

	protected void printHead(PrintWriter out) {
		out.println("<html>");
		out.println("<head><link rel=\"stylesheet\" href=\"styles.css\"><title>Servlet With a Session</title></head>");
		out.println("<body>");
	}

	protected void printForm(PrintWriter out) {
		if (!mapPerson.isEmpty()) {
			System.out.println("\n\nmap is not empty\n\n");
			out.println(
					"<table id=\"table1\"><caption>Persons in this session</caption><tr><th>Key</th><th>First name</th><th>Last name</th></tr>");
			for (Person p : mapPerson.values()) {
				out.println("<tr>");
				out.println("<td>" + p.getKey() + "</td>");
				out.println("<td>" + p.getFirstName() + "</td>");
				out.println("<td>" + p.getLastName() + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		}

		out.println("<form method=POST action=SessionServlet>");

		out.println(
				"<table><caption>Manage persons in this session</caption><tr><th>Key</th><th>First name</th><th>Last name</th></tr>");
		out.println("<tr>");
		out.println("<td><input type=text name=key></td>");
		out.println("<td><input type=text name=firstName></td>");
		out.println("<td><input type=text name=lastName></td>");
		out.println("<tr><td colspan=\"3\"><input type=submit name=button value='Add'>");
		out.println("<input type=submit name=button value='Remove'></td></tr>");
		out.println("</table>");

		out.println("</form>");
	}
}

class Person {
	private String key;
	private String firstName;
	private String lastName;

	public Person(String key, String firstName, String lastName) {
		this.key = key;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getKey() {
		return this.key;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
