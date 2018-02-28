<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="dao.TypesDao"%>
<%@page import="java.util.List"%>
<%@page import="entity.Types"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Category"%>
<%@page import="dao.CategoryDao"%>
<html>
	<head>
		<script src="Js/prototype.lite.js" type="text/javascript">
</script>
		<script src="Js/moo.fx.js" type="text/javascript">
</script>
		<script src="Js/moo.fx.pack.js" type="text/javascript">
</script>
		<link rel="stylesheet" type="text/css" href="css/skin.css" />
		<script type="text/javascript">
window.onload = function() {
	var contents = document.getElementsByClassName('content');
	var toggles = document.getElementsByClassName('type');

	var myAccordion = new fx.Accordion(toggles, contents, {
		opacity : true,
		duration : 400
	});
	myAccordion.showThisHideOpen(contents[0]);
	for ( var i = 0; i < document.getElementsByTagName("a").length; i++) {
		var dl_a = document.getElementsByTagName("a")[i];
		dl_a.onfocus = function() {
			this.blur();
		}
	}
	
}
</script>
	</head>

	<body >
		<table width="100%"  border="0" cellpadding="0"
			cellspacing="0" bgcolor="#EEF2FB">
			<tr>
				<td width="182" valign="top">
					<div id="container">
						<h1 class="type">
							<a href="javascript:void(0)" onclick="showAddClassPage()">课程设置</a>
						</h1>
						<div class="content">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<img src="Images/menu_top_line.gif" width="182" height="5" />
									</td>
								</tr>
							</table>
							<ul class="RM">
								<%
									TypesDao dao = new TypesDao();
									List<Types> types = new ArrayList<Types>();
									types = dao.selectTypes();
									for (int i = 0; i < types.size(); i++) {
								%>
								<li>
									<a href="add_set.jsp?typeId=<%=types.get(i).getFunctionId()%>&childrenName=<%=types.get(i).getChildrenName()%>" target="main"><%=types.get(i).getFunctionName()%>课程设置</a>
								</li>
								<%
									}
								%>
								<li>
									<a href="type.jsp" target="main">类型设置</a>
								</li>
							</ul>
							
						</div>
						
						<h1 class="type">
							<a href="javascript:void(0)">视频设置</a>
						</h1>
						<div class="content">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<img src="Images/menu_top_line.gif" width="182" height="5" />
									</td>
								</tr>
							</table>
							<ul class="RM">
								<li>
									<a href="video.jsp" target="main">视频内容设置</a>
								</li>
							</ul>
						</div>
						
						<h1 class="type">
							<a href="javascript:void(0)">试卷设置</a>
						</h1>
						<div class="content">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<img src="Images/menu_top_line.gif" width="182" height="5" />
									</td>
								</tr>
							</table>
							<ul class="RM">
							<%
									TypesDao dao1 = new TypesDao();
									List<Types> types1 = new ArrayList<Types>();
									types1 = dao1.selectTypes();
									for (int i = 0; i < types1.size(); i++) {
								%>
								<li>
									<a href="test_pager.jsp?typeName=<%=types1.get(i).getFunctionName()%>" target="main"><%=types1.get(i).getFunctionName()%> 类试卷设置</a>
								</li>
								<%
									}
								%>
								<li>
									<a href="test_pager.jsp?typeName='mixType'" target="main">综合类试卷设置</a>
								</li>
							</ul>
						</div>
						
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
