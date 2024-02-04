package com.jookebox.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.jookebox.dao.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public void init() {
		userDao = new UserDao();
	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        
	        String username = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        if(userDao.validateUser(username, password))
	        {
	        	out.println("<html><body>");
	        	out.println("Welcome to Jooke Box");
	        	out.println("</body></html>");
	        }
	        else
	        {
	        	out.println("<html><body>");
	        	out.println("Username or Password incorrect");
	        	out.println("</body></html>");
	        }
			
	    }  
}
