package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import entity.Category;

public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		
		String name = request.getParameter("categoryAdd");
		String parentId1 = request.getParameter("parentId");
		int parentId = Integer.parseInt(parentId1);
		if(name==null || name.equals("")){
			out.print("添加失败，请输入数据");
		}else{
			Category category = new Category();
			category.setShceduleName(name);
			category.setParentId(parentId);
			CategoryDao dao = new CategoryDao();
			int i= dao.addCategory(category);
			if(i > 0){
				out.print("添加成功");
			}else{
				out.print("添加失败，请重新添加");
			}
		}
		
	}

}
