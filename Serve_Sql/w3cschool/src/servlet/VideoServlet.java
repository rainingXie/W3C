package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VideoDao;
import entity.Video;

public class VideoServlet extends HttpServlet {
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
		
		String videoName = request.getParameter("videoName");
		String videoType = request.getParameter("videoType");
		String videoLength = request.getParameter("videoLength");
		String videoAddress = request.getParameter("videoAddress");
		
		if(videoName == null && ("").equals(videoName)){
			out.print("视频名称不能为空");
		}else if(videoType == null && ("").equals(videoType)){
			out.print("视频类型不能为空");
		}else if(videoLength == null && ("").equals(videoLength)){
			out.print("视频长度不能为空");
		}else if(videoAddress == null && ("").equals(videoAddress)){
			out.print("视频地址不能为空");
		}else{
			VideoDao dao = new VideoDao();
			Video video = new Video();
			video.setName(videoName);
			video.setLabel(videoType);
			video.setTotalTime(videoLength);
			video.setUrl(videoAddress);
			int i = dao.addVideo(video);
			if(i > 0){
				out.print("添加成功");
			}else{
				out.print("添加失败，请重新添加");
			}
		}
	}
}
