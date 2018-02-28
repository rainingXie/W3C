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
							<%int typeId = Integer.parseInt(request.getParameter("typeId"));
							  String childrenName = request.getParameter("childrenName"); %>
								<table width="100%">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input class="btn" type="button" style="width: 200px;height: 35px;font-size: 36sp"
									 onclick="window.location='category.jsp?typeId=<%=typeId %>';" value="添加课程类型相关信息"/>
									(点击按钮进入添加课程类型设置相关界面)
								<br/><br/><br/><br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input class="btn" type="button" style="width: 200px;height: 35px;font-size: 36sp"
									onclick="window.location='content.jsp?childrenName=<%=childrenName %>';" value="添加课程内容相关信息" onclick="linkContent()"/>
								    (点击按钮进入添加课程内容设置相关界面)
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
