<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>课程类型设置</title>
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
												请填写添加的课程类型的相关信息
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
											<form action="TypesServlet" method="post">
												<table width="100%" class="cont">
													<tr>
														<td width="2%">
															&nbsp;
														</td>
														<td width="15%">
															课程类型名称：
														</td>
														<td width="50%">
																<input class="text" type="text" name="typeName" value="" style="width: 90%"/>
															</td>
														<td>
															设置课程类型
														</td>
														<td width="2%">
															&nbsp;
														</td>
													</tr>

													<tr>
														<td>
															&nbsp;
														</td>
														<td>
															描述(Description)：
														</td>
														<td>
															<textarea style="width: 90%" name="introduce"></textarea>
														</td>
														<td>
															课程类型的简短描述
														</td>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td>
															&nbsp;
														</td>
														<td colspan="3" align="center">
															<input class="btn" type="submit" style="width:150px" value="提交" />
														</td>
														<td>
															&nbsp;
														</td>
													</tr>
												</table>
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