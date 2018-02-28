package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.CreateTable;
import utils.FindTableNameDao;

import dao.CategoryDao;
import dao.ContentDao;
import dao.ContentListDao;
import entity.Category;
import entity.Content;
import entity.ContentList;

public class ContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text[回头]ml");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String contentListTableName = request
				.getParameter("contentListTableName");
		String contentTableName = request.getParameter("contentTableName");
		String contentList = request.getParameter("contentList");
		String contentListAdd = request.getParameter("contentListAdd");
		String categoryName = request.getParameter("category");
		String contentTitle = request.getParameter("contentTitle");
		String content = request.getParameter("content");
		String code = request.getParameter("code");
		
        ContentListDao contentListDao = new ContentListDao();
		if (contentListTableName != null && !("").equals(contentListTableName)) {
			if(categoryName != null && !("").equals(categoryName)){
				CategoryDao dao = new CategoryDao();
				int id = dao.selectCategorysByName(categoryName);
				if(id >-1){
					if(contentListAdd != null && !("").equals(contentListAdd)){
						ContentList list = new ContentList();
						list.setTitle(contentListAdd);
						list.setParentId(id);
						int i = contentListDao.addContentList(list, contentListTableName);
						if(i>0){
							out.print("添加内容类型成功");
						}else{
							out.print("添加内容类型失败");
						}
					}
				}else{
					out.append("添加失败");
				}
				
			}else{
				out.print("请填写课程类型");
			}
			
			FindTableNameDao tableName = new FindTableNameDao();
			boolean flag = tableName.findTableByTableName(contentListTableName);
			if (flag) {
				if (contentTableName != null && !("").equals(contentTableName)) {
					boolean flag2 = tableName
							.findTableByTableName(contentTableName);
					if (flag2) {
						if(contentList != null && !("").equals(contentList)){
							List<Integer> list = contentListDao.findCategoryIdByName(contentList, contentTableName);
							int parentId = list.get(0);
							Content content2 = new Content();
							if(contentTitle != null && !("").equals(contentTitle)){
								content2.setTitle(contentTitle);
								content2.setContentText(content);
								content2.setTryCode(code);
								content2.setParentId(parentId);
								ContentDao dao = new ContentDao();
								dao.addContentList(content2, contentTableName);
							}
						}
					} else {
						CreateTable createTable = new CreateTable();
						int i=createTable.createNoFindTable(contentTableName);
						if(i>0){
							out.print("创建表第四层成功");
						}else{
							out.print("创建表第四层失败");
						}
					}
				} else {
					out.print("添加失败，请重新添加");
				}
			}else{
				CreateTable createTable = new CreateTable();
				int i=createTable.createNoFindTable(contentListTableName);
				if(i>0){
					out.print("创建表第三层成功");
				}else{
					out.print("创建表第三层失败");
				}
			}
		}
	}
}