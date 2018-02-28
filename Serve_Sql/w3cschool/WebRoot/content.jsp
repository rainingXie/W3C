<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="dao.CategoryDao"%>
<%@page import="entity.Category"%>
<%@page import="dao.ContentListDao"%>
<%@page import="entity.ContentList"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>课程内容设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/skin.css" />
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!-- 头部开始 -->
			<tr>
				<td width="17" valign="top" background="Images/mail_left_bg.gif">
					<img src="Images/left_top_right.gif" width="17" height="29" />
				</td>
				<td valign="top" background="Images/content_bg.gif">
					<table width="100%" height="31" border="0" cellpadding="0"
						cellspacing="0" background="Images/content_bg.gif">
						<tr>
							<td height="31">
								<div class="title">
									添加数据
								</div>
							</td>
						</tr>
					</table>
				</td>
				<td width="16" valign="top" background="Images/mail_right_bg.gif">
					<img src="Images/nav_right_bg.gif" width="16" height="29" />
				</td>
			</tr>
			<!-- 中间部分开始 -->
			<tr>
				<!--第一行左边框-->
				<td valign="middle">
					&nbsp;
				</td>
				<!--第一行中间内容-->
				<td valign="top" bgcolor="#F7F8F9">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<!-- 空白行-->
						<tr>
							<td colspan="2" valign="top">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td valign="top">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<table>
									<tr>

										<td valign="bottom">
											<h3 style="letter-spacing: 1px;">
												请填写添加的课程内容的相关信息
											</h3>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- 一条线 -->
						<tr>
							<td height="40" colspan="4">
								<table width="100%" height="1" border="0" cellpadding="0"
									cellspacing="0" bgcolor="#CCCCCC">
									<tr>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- 添加开始 -->
						<tr>
							<td width="2%">
								&nbsp;
							</td>
							<td width="96%">
							<%String childrenName = request.getParameter("childrenName");
							  String tableName = childrenName+"_category";
							  String contentTable = childrenName+"_content"; %>
							  <input name="contentListTableName" value="<%=tableName%>" style="display: none;"/>
							  <input name="contentTableName" value="<%=contentTable%>" style="display: none;"/>
								<table width="100%">
									<tr>
										<td colspan="2">
											<form action="ContentServlet" method="post">
											
    										<%ContentListDao dao2 = new ContentListDao();%>											   
											 <% List<ContentList> contentLists = new ArrayList<ContentList>(); 
											  contentLists = dao2.selectContentList(tableName);
											  StringBuilder sb = new StringBuilder();
											  for(int i=0;i<contentLists.size()-1;i++){
											  	sb.append(contentLists.get(i).getTitle());
											  	sb.append(",");%>
											 <%} 
											 sb.append(contentLists.get(contentLists.size()-1).getTitle());%>  
											  已存在的内容类型有：<%=sb.toString() %> 
											  <br/><br/>
												内容类型名称：
												<select style="width: 50%" name="contentList"/>
													<option ></option>	
													  <% List<ContentList> contentLists2 = new ArrayList<ContentList>(); 
											  			 contentLists2 = dao2.selectContentList(tableName);
											 			 for(int i=0;i<contentLists2.size();i++){%>						 
											   			<option  value="<%=contentLists2.get(i).getId() %>"><%=contentLists2.get(i).getTitle()%></option>
											   		<%} %>
													</select>（可添加）
												<br/><br/>
												 添加内容类型名称：（若上述下拉表中不含该 内容类型名称，可在下框中自行添加）
												<br/>
												<input class="text" type="text" name="contentListAdd" value=""
													style="width: 66%" />
												<br/><br/>
												添加的内容类型所属的课程类型
												<input class="text" type="text" name="category" value=""
													style="width: 35%" />
												<br/><br/><br/>
												 内&nbsp;&nbsp;容&nbsp;&nbsp;标&nbsp;&nbsp;题：
												<input class="text" type="text" name="contentTitle" value=""
													style="width: 51%" />
												<br/><br/>
												内 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：
												<br/>
												<textarea style="width: 90%; height: 100px" name="content"></textarea>
												<br/><br/>
												代码（若内容中含亲自试一试）：
												<br/>
												<textarea style="width: 90%; height: 100px" name="code"></textarea>
												<br/><br/><br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="btn" type="submit" style="width: 150px;height: 30px;font-size: 28sp"
													value="提交" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="btn" type="reset" style="width: 150px;height: 30px;font-size: 28sp"
													value="重置" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="btn" type="reset" style="width: 150px;height: 30px;font-size: 28sp"
													onclick="history.go(-1)" value="返回" />
											</form>
										</td>
									</tr>
								</table>
							</td>
							<td width="2%">
								&nbsp;
							</td>
						</tr>

						<td background="Images/mail_right_bg.gif">
							&nbsp;
						</td>
						</tr>
						<tr>
							<td height="200" colspan="4"></td>
						</tr>
						<!-- 底部部分 -->
						<tr>
							<td valign="bottom" background="Images/mail_left_bg.gif">
								<img src="Images/buttom_left.gif" width="17" height="17" />
							</td>
							<td background="Images/buttom_bgs.gif">
								<img src="Images/buttom_bgs.gif" width="17" height="17">
							</td>
							<td valign="bottom" background="Images/mail_right_bg.gif">
								<img src="Images/buttom_right.gif" width="16" height="17" />
							</td>
						</tr>
					</table>
	</body>
</html>
