package servlet;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import sun.misc.BASE64Decoder;
import util.DateUtil;
import util.WebUtil;

import dao.UserInfoDao;
import entity.UserInfo;

public class AppLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public AppLoginServlet(){
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json; charset=UTF-8");
        UserInfoDao uInfoDao = new UserInfoDao();
        String name = request.getParameter("userNo");
        String pwd = request.getParameter("userPwd");
        String flag = request.getParameter("headImage");
        String update = request.getParameter("update");
        if("1".equals(flag)) {
            String student = request.getParameter("uhead");
            String json = "";
            String headName = DateUtil.getCurDate("yyMMddss");
            if(student != null) {
                String temp = student.substring(0, 4);
                if(temp.equals("http")) {
                    json = student;
                } else {
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] b = decoder.decodeBuffer(student);

                    for(int imgFilePath = 0; imgFilePath < b.length; ++imgFilePath) {
                        if(b[imgFilePath] < 0) {
                            b[imgFilePath] = (byte)(b[imgFilePath] + 256);
                        }
                    }

                    String var18 = super.getServletContext().getRealPath("/img/user_img/" + headName + ".png");
                    FileOutputStream os = new FileOutputStream(var18);
                    os.write(b);
                    os.flush();
                    os.close();
                    json = WebUtil.getServerUrl(request) + "/img/user_img/" + headName + ".png";
                }

                if(uInfoDao.updateImg(json, name) > 0) {
                    response.getWriter().print(json);
                } else {
                	response.getWriter().print("");
                }
            }
        } else if(!"1".equals(update)) {
            UserInfo var16 = uInfoDao.login(name, pwd);
            JSONObject var17;
            if(var16 != null) {
                var17 = JSONObject.fromObject(var16);
                response.getWriter().print(var17.toString());
            } else {
                var17 = JSONObject.fromObject("{\"info\":\"bad users\"}");
                response.getWriter().print(var17.toString());
            }
        } else if(uInfoDao.updatePwd(pwd, name) > 0) {
            response.getWriter().print("1");
        } else {
        	response.getWriter().print("0");
        }

    }	
}
