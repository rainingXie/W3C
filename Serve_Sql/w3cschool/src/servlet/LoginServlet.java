package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");	
		
		String name = request.getParameter("managerName");
		String password = request.getParameter("password");
		
		boolean b = false;
		if(name != null && password != null){
			if(name.equals("111") && password.equals("111")){
				b = true;
			}
			
			if(b == true){
				response.sendRedirect(request.getContextPath()+"/index.html");
			}else{
				response.sendRedirect("../login.html");
			}
			
		}else{
			
		}
	}

}
