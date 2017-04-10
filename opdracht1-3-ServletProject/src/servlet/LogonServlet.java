package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogonServlet")
public class LogonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Logon Servlet</title></head>");				
		out.println("<body>");
		printForm(out);
		out.println("</body>");
		out.println("</html>");
	}
	
	protected void printForm(PrintWriter out) {
		out.println("<h3>Please log in with Username 'user' and Password 'pass'.</h3>");
		out.println("<form method=POST action=LogonServlet>");
		out.println("Username = <input type=text name=username><br>");
		out.println("Password = <input type=text name=password>");
		out.println("<input type=submit value='Logon'>");
		out.println("</form>");		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");		
	
		out.println("<html>");
		out.println("<body>");
		
		if(userName.equals("user") && password.equals("pass")) {
			out.printf("<h3>Welcome %s</h3>", userName);
		} else {
			out.println("<h3>Wrong userid or password! Please try again.</h3>");
			printForm(out);
		}
		
		out.println("</body>");
		out.println("</html>");
	}

}
