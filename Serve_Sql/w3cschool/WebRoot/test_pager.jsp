<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="dao.TestPagerDao"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <head>
    <title>试卷设置</title>
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
												请填写添加的试卷的相关信息
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
								<table width="100%">
									<tr>
										<td colspan="2">
											<form action="TestPagerServlet" method="post">
											        
												试卷名称：
												<select  style="width: 300px"  name="testName"/>
														<option ></option>
														
														<%TestPagerDao dao = new TestPagerDao();
														  List<String> list = new ArrayList<String>();
														  list = dao.allName();
														  for(int i=0;i<list.size();i++){ %>
														  <option value="<%=list.get(i) %>"><%=list.get(i)%></option>
														  <%} %>
														
												</select>（可添加）
												<%String typeName = request.getParameter("typeName"); %>
												<input name="type" value="<%=typeName%>" style="visibility: hidden;">
												<br/>
												<br/>
												添加试卷名称：(如果选择下拉表中无该试卷名称，可在下面编辑框中自行添加)
												<br/>
												<input class="text" type="text" name="testNameAdd" value="" style="width: 500px"/>
												<br/>
												<br/>
												试&nbsp;&nbsp;&nbsp;&nbsp;卷&nbsp;&nbsp;&nbsp;&nbsp;内&nbsp;&nbsp;&nbsp;&nbsp;容：
												<br/>
												
												题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：
												<input class="text" type="text" name="testTitle" value="" style="width: 600px"/>
												<br/>
												<br/>
												选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项：
												<br/>
												&nbsp;A&nbsp;.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="text" type="text" name="itemA" value="" style="width: 605px"/> 
												<br/><br/>
												&nbsp;B&nbsp;.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="text" type="text" name="itemB" value="" style="width: 605px"/>
												<br/><br/>
												&nbsp;C&nbsp;.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="text" type="text" name="itemC" value="" style="width: 605px"/>
												<br/><br/>
												&nbsp;D&nbsp;.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="text" type="text" name="itemD" value="" style="width: 605px"/>
												<br/><br/>
												答&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;案：
												<input class="text" type="text" name="answer" value="" style="width: 200px"/>
												<br/><br/>
												<br/><br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input class="btn" type="submit" style="width:150px; font-size: 24sp" value="提交" />
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
