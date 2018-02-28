package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import dao.ApkInfoDao;
import dao.CategoryDao;
import dao.ContentDao;
import dao.ContentListDao;
import dao.DiscussDao;
import dao.NoteDao;
import dao.TestPagerDao;
import dao.TypesDao;
import dao.UserCollectDao;
import dao.UserInfoDao;
import dao.VideoDao;
import entity.ApkInfo;
import entity.Discuss;
import entity.UserInfo;

@SuppressWarnings("serial")
public class GetDataServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 鐢ㄦ埛
		String userPwd = request.getParameter("userPwd");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String img = request.getParameter("img");

		String str = request.getParameter("find");
		String tableName = request.getParameter("tableName");
		String parentId = request.getParameter("parentId");
		String userNo = request.getParameter("userNo");
		// 绗旇鐨勬坊鍔犱笌鍒犻櫎
		String idStr = request.getParameter("id");
		String nameStr = request.getParameter("name");
		String contentStr = request.getParameter("content");
		String dateStr = request.getParameter("date");
		String userNoStr = request.getParameter("userNo");
		// 鏀惰棌涓庡彇娑?
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String code = request.getParameter("code");
		String user = request.getParameter("userNo");
		String testTag = request.getParameter("testTag");
		String tryCode = request.getParameter("tryCode");
		// 璇曞嵎
		String tag = request.getParameter("tag");
		// 璇勮
		String type = request.getParameter("type");
		String no = request.getParameter("no");
		String isQuote = request.getParameter("isQuote");
		String floor = request.getParameter("floor");
		String quoteFloor = request.getParameter("quoteFloor");
		String userName = request.getParameter("userName");

		if (str.equals("contents") && tableName != null) {
			// 瑙ｆ瀽绗洓灞?鍐呭
			try {
				this.selectContents(request, response, tableName,
						Integer.parseInt(parentId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (str.equals("types")) {
			// 瑙ｆ瀽绗竴灞?绫诲瀷
			try {
				this.selectTypes(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (str.equals("categorys")) {
			// 瑙ｆ瀽绗簩灞?璇剧▼琛ㄧ殑鐩綍
			try {
				this.selectCategorys(request, response,
						Integer.parseInt(parentId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (str.equals("contentList") && tableName != null) {
			// 瑙ｆ瀽绗笁灞?鍐呭鐨勫垪琛?
			try {
				this.selectContentList(request, response, tableName,
						Integer.parseInt(parentId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (str.equals("userCollect") && userNo != null) {
			// 鏌ユ壘鎵€鏈夋敹钘?
			this.selectUserCollect(request, response, userNo);
		} else if (str.equals("userCollectInsert")) {
			// 娣诲姞鏀惰棌
			this.addUserCollect(request, response, title, content, code, user,
					Integer.parseInt(testTag), tryCode);
		} else if (str.equals("userCollectDelete") && id != null) {
			// 鍒犻櫎鏀惰棌
			this.deleteUserCollect(request, response, Integer.parseInt(id));
		} else if (str.equals("notes") && userNoStr != null) {
			// 鏌ユ壘鎵€鏈夌瑪璁?
			this.selectNotes(request, response, userNoStr);
		} else if (str.equals("notesInsert")) {
			// 娣诲姞绗旇
			this.addNote(request, response, nameStr, contentStr, dateStr,
					userNoStr);
		} else if (str.equals("notesDelete") && idStr != null) {
			// 鍒犻櫎绗旇
			this.deleteNote(request, response, Integer.parseInt(idStr));
		} else if (str.equals("allpagers")) {
			this.selectAllpagers(request, response);
		} else if (str.equals("testpagers") && tag != null) {
			// 鑾峰彇璇曞嵎
			this.selectTestpagers(request, response, Integer.parseInt(tag));
		} else if (str.equals("allCategorys")) {
			// 瑙ｆ瀽绗簩灞?璇剧▼琛ㄧ殑鐩綍
			try {
				this.selectCategorys(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (str.equals("videos")) {
			// 鑾峰彇鎵€鏈夌殑瑙嗛
			this.selectVideo(request, response);
		} else if (str.equals("apkInfos")) {
			// 鑾峰彇App鐨勪俊鎭?
			ApkInfoDao dao = new ApkInfoDao();
			ApkInfo apk = dao.selectApk();
			JSONObject info;
			if (apk != null) {
				info = JSONObject.fromObject(apk);
				response.getWriter().print(info.toString());
			} else {
				info = JSONObject.fromObject("{\"info\":\"bad users\"}");
				response.getWriter().print(info.toString());
			}
		} else if (str.equals("discuss")) {
			// 鑾峰彇鎵€鏈夎瘎璁轰俊鎭?
			this.selectDiscusses(request, response);
		} else if (str.equals("discussItem") && null != parentId
				&& null != type) {
			// 鑾峰彇鏌愪竴绡囩珷鐨勮瘎璁轰俊鎭?
			this.selectDiscuss(request, response, Integer.parseInt(parentId),
					type);
		} else if (str.equals("addDiscuss")) {
			// 娣诲姞璇勮淇℃伅
			this.addDiscuss(request, response,content, dateStr,
					Integer.parseInt(isQuote), Integer.parseInt(quoteFloor),
					Integer.parseInt(floor), Integer.parseInt(parentId), type,
					no);
		} else if (str.equals("addUser")) {
			// 娣诲姞鐢ㄦ埛
			this.addUserInfo(request, response, userNo, userName, userPwd,
					province, city);
		} else if (str.equals("allUser")) {
			//鏌ユ壘鎵€鏈夌敤鎴?
			this.selectAllUser(request, response);
		}else if (str.equals("addQQUser")) {
			// 娣诲姞QQ鐢ㄦ埛
			this.addQQUserInfo(request, response, userNo, userName, img,
					province, city);
		}

	}
	
	private void addQQUserInfo(HttpServletRequest request,
			HttpServletResponse response, String userNo, String userName,
			String img, String province, String city)
			throws UnsupportedEncodingException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		UserInfoDao dao = new UserInfoDao();
		dao.addUserInfo(userNo, userName, img, province, city);

	}

	private void selectAllUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		UserInfoDao dao = new UserInfoDao();
		List<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
		users = dao.selectAllUserInfo();
		if (users == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < users.size(); i++) {
				HashMap<String, Object> user = users.get(i);
				try {
					stringer.object();
					Iterator e = user.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(user.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
		
	}

	private void addUserInfo(HttpServletRequest request,
			HttpServletResponse response, String userNo, String userName,
			String userPwd, String province, String city)
			throws UnsupportedEncodingException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		UserInfoDao dao = new UserInfoDao();
		dao.addUserInfo(userNo, userName, userPwd, province, city);

	}

	private void addDiscuss(HttpServletRequest request,
			HttpServletResponse response,
			String content, String dateStr, int isQuote, int quoteFloor,
			int floor, int parentId, String type, String no)
			throws UnsupportedEncodingException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		DiscussDao dao = new DiscussDao();
		dao.insertDiscuss(content, dateStr, isQuote, quoteFloor,
				floor, parentId, type, no);

	}

	private void selectDiscuss(HttpServletRequest request,
			HttpServletResponse response, int parentId, String type)
			throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DiscussDao dao = new DiscussDao();
		List<HashMap<String, Object>> discusses = new ArrayList<HashMap<String, Object>>();
		discusses = dao.findDiscuss(parentId, type);
		if (discusses == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < discusses.size(); i++) {
				HashMap<String, Object> discuss = discusses.get(i);
				try {
					stringer.object();
					Iterator e = discuss.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(discuss.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();

	}

	private void selectDiscusses(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DiscussDao dao = new DiscussDao();
		List<HashMap<String, Object>> discusses = new ArrayList<HashMap<String, Object>>();
		discusses = dao.selectDiscusses();
		if (discusses == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < discusses.size(); i++) {
				HashMap<String, Object> discuss = discusses.get(i);
				try {
					stringer.object();
					Iterator e = discuss.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(discuss.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	private void selectVideo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		VideoDao dao = new VideoDao();
		List<HashMap<String, Object>> videos = new ArrayList<HashMap<String, Object>>();
		videos = dao.selectAllVideos();
		if (videos == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < videos.size(); i++) {
				HashMap<String, Object> video = videos.get(i);
				try {
					stringer.object();
					Iterator e = video.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(video.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	private void selectAllpagers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		TestPagerDao dao = new TestPagerDao();
		List<HashMap<String, Object>> testpagers = new ArrayList<HashMap<String, Object>>();
		testpagers = dao.selectAllpagers();
		if (testpagers == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < testpagers.size(); i++) {
				HashMap<String, Object> testpager = testpagers.get(i);
				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = testpager.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(testpager.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	private void selectTestpagers(HttpServletRequest request,
			HttpServletResponse response, int tag) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		TestPagerDao dao = new TestPagerDao();
		List<HashMap<String, Object>> testpagers = new ArrayList<HashMap<String, Object>>();
		testpagers = dao.selectTestpagers(tag);
		if (testpagers == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < testpagers.size(); i++) {
				HashMap<String, Object> testpager = testpagers.get(i);
				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = testpager.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(testpager.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	private void deleteNote(HttpServletRequest request,
			HttpServletResponse response, int id)
			throws UnsupportedEncodingException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		NoteDao dao = new NoteDao();
		dao.deleteNote(id);
	}

	private void addNote(HttpServletRequest request,
			HttpServletResponse response, String nameStr, String contentStr,
			String dateStr, String userNoStr)
			throws UnsupportedEncodingException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		NoteDao dao = new NoteDao();
		dao.addNote(nameStr, contentStr, dateStr, userNoStr);
	}

	private void selectNotes(HttpServletRequest request,
			HttpServletResponse response, String userNoStr) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		NoteDao dao = new NoteDao();
		List<HashMap<String, Object>> notes = new ArrayList<HashMap<String, Object>>();
		notes = dao.selectAllNote(userNoStr);
		if (notes == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < notes.size(); i++) {
				HashMap<String, Object> note = notes.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = note.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(note.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	private void selectUserCollect(HttpServletRequest request,
			HttpServletResponse response, String userNo) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		UserCollectDao dao = new UserCollectDao();
		List<HashMap<String, Object>> userCollects = new ArrayList<HashMap<String, Object>>();
		userCollects = dao.selectAllUserCollect(userNo);
		if (userCollects == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < userCollects.size(); i++) {
				HashMap<String, Object> userCollect = userCollects.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = userCollect.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(userCollect.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();

	}

	private void addUserCollect(HttpServletRequest request,
			HttpServletResponse response, String title, String content,
			String code, String user, int testTag, String tryCode)
			throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		UserCollectDao dao = new UserCollectDao();
		dao.addUserCollect(title, content, code, user, testTag, tryCode);
	}

	private void deleteUserCollect(HttpServletRequest request,
			HttpServletResponse response, int id) throws IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		UserCollectDao dao = new UserCollectDao();
		dao.deleteUserCollect(id);
	}

	public void selectContents(HttpServletRequest request,
			HttpServletResponse response, String tableName, int parentId)
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ContentDao dao = new ContentDao();
		List<HashMap<String, Object>> contents = new ArrayList<HashMap<String, Object>>();
		contents = dao.selectAllContent(tableName, parentId);
		if (contents == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < contents.size(); i++) {
				HashMap<String, Object> content = contents.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = content.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(content.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	public void selectTypes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		TypesDao dao = new TypesDao();
		List<HashMap<String, Object>> types = new ArrayList<HashMap<String, Object>>();
		types = dao.selectAllTypes();
		if (types == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < types.size(); i++) {
				HashMap<String, Object> type = types.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = type.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(type.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	public void selectCategorys(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		CategoryDao dao = new CategoryDao();
		List<HashMap<String, Object>> categorys = new ArrayList<HashMap<String, Object>>();
		categorys = dao.selectAllCategory();
		if (categorys == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < categorys.size(); i++) {
				HashMap<String, Object> category = categorys.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = category.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(category.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	public void selectCategorys(HttpServletRequest request,
			HttpServletResponse response, int parentId)
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		CategoryDao dao = new CategoryDao();
		List<HashMap<String, Object>> categorys = new ArrayList<HashMap<String, Object>>();
		categorys = dao.selectAllCategory(parentId);
		if (categorys == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < categorys.size(); i++) {
				HashMap<String, Object> category = categorys.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = category.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(category.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}

	public void selectContentList(HttpServletRequest request,
			HttpServletResponse response, String tableName, int parentId)
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ContentListDao dao = new ContentListDao();
		List<HashMap<String, Object>> contentLists = new ArrayList<HashMap<String, Object>>();
		contentLists = dao.selectAllContentList(tableName, parentId);
		if (contentLists == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < contentLists.size(); i++) {
				HashMap<String, Object> contentList = contentLists.get(i);

				try {
					stringer.object();
					@SuppressWarnings("rawtypes")
					Iterator e = contentList.keySet().iterator();
					while (e.hasNext()) {
						Object key = e.next();
						stringer.key((String) key).value(contentList.get(key));
					}
					stringer.endObject();
				} catch (JSONException var11) {
					var11.printStackTrace();
				}
			}
			stringer.endArray();
			out.print(stringer.toString());
		}

		out.flush();
		out.close();
	}
}