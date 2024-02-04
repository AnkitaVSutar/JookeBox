package com.jookebox.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.jookebox.dao.UserDao;
import com.jookebox.exception.EmailValidation;
import com.jookebox.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterationServlet extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;

	private UserDao userDao;

	public void init() {
		userDao = new UserDao();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// We need printwriter object to write html content
		PrintWriter pw = response.getWriter();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String contact = request.getParameter("contact");

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setContact(contact);
		user.setAddress(address);
		int registered = 0;
		try {
			registered = userDao.register(user);
		} catch (EmailValidation e) {
			// writing html in the stream
			pw.println("<html><body>");
			pw.println(e.getMessage());
			pw.println("</body></html>");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// writing html in the stream
		if (registered != 0) {
			pw.println("<html><body>");
			pw.println("Welcome to Jooke Box");
			pw.println("</body></html>");

		}
	}

}
