package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TestPagerDao;

import entity.TestPager;

public class TestPagerServlet extends HttpServlet {
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
		
		String testName = request.getParameter("testName");
		String testNameAdd = request.getParameter("testNameAdd");
		String type = request.getParameter("type");
		String testTitle = request.getParameter("testTitle");
		String itemA = request.getParameter("itemA");
		String itemB = request.getParameter("itemB");
		String itemC = request.getParameter("itemC");
		String itemD = request.getParameter("itemD");
		String answer = request.getParameter("answer");
		
		if(testName==null || ("").equals(testName)){
			if(testNameAdd==null || ("").equals(testNameAdd)){
				out.print("试卷名称不能为空");
			}	
		}else if(testNameAdd != null && !("").equals(testNameAdd)){
			out.print("试卷名称不能重复填写");
		}else if(testTitle == null || ("").equals(testTitle)){
			out.print("题目不能为空");
		}else if(itemA == null || ("").equals(itemA)){
			out.print("选项A不能为空");
		}else if(itemB == null || ("").equals(itemB)){
			out.print("选项B不能为空");
		}else if(itemC == null || ("").equals(itemC)){
			out.print("选项C不能为空");
		}else if(itemD == null || ("").equals(itemD)){
			out.print("选项D不能为空");
		}else if(answer == null || ("").equals(answer)){
			out.print("答案不能为空");
		}else if(testName != null && !("").equals(testName)){
			TestPagerDao dao = new TestPagerDao();
			List<Integer> tags = dao.findTagByName(testName);
			TestPager subject = new TestPager();
			subject.setTitle(testTitle);
			subject.setItemA(itemA);
			subject.setItemB(itemB);
			subject.setItemC(itemC);
			subject.setItemD(itemD);
			subject.setAnswer(answer);
			if(tags != null && tags.size()>0){
				subject.setTag(tags.get(0));
			}			
			int i = dao.addTestPager2(subject);
			if(i > 0){
				out.print("添加成功");
			}else{
				out.print("添加失败，请重新添加");
			}
		}
		
		if((testNameAdd != null && !("").equals(testNameAdd)) && (testName==null || ("").equals(testName))){
			TestPagerDao dao = new TestPagerDao();
			List<Integer> allTag = dao.allTagByFlagtag();
			int tag = allTag.get(allTag.size()-1)+1;
			TestPager subject = new TestPager();
			subject.setName(testNameAdd);
			subject.setTitle(testTitle);
			subject.setItemA("A." + itemA);
			subject.setItemB("B." + itemB);
			subject.setItemC("C." + itemC);
			subject.setItemD("D." + itemD);
			subject.setAnswer(answer);
			subject.setTag(tag);
			subject.setFlagTag(1);
			subject.setType(type);
			int i = dao.addTestPager(subject);
			if(i > 0){
				out.print("添加成功");
			}else{
				out.print("添加失败，请重新添加");
			}
		}
	}

}
