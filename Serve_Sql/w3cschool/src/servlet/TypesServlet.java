package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TypesDao;

import entity.Types;

public class TypesServlet extends HttpServlet {
	private static final long serialVersionUID = -48163504658553553L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();	
		
		String functionName = request.getParameter("typeName");
		String introduce = request.getParameter("introduce");
		String childrenName = functionName.toLowerCase();
		out.print(childrenName);
		if(functionName == null){
			out.print("名称不能为空");
		}else if(introduce == null){
			out.print("简介不能为空");
		}else if(functionName != null && introduce != null){
			//添加
			Types types = new Types();
			types.setFunctionName(functionName);
			types.setIntroduce(introduce);
			types.setChildrenName(childrenName);
			TypesDao dao = new TypesDao();
			int i = dao.addType(types);
			if(i>0){
				out.print("添加类型成功");
			}else{
				out.print("添加类型失败,已存在");
			}
			
		}
		
	}

}
