<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="DTO.View" %>
<% ArrayList<View> viewList = new ArrayList<View>();
viewList = (ArrayList<View>)request.getAttribute("view");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp"%>
	<!-- 회원리스트를 ArrayList에 넣고. -->
	<!-- for문으로 표를 만든다. -->
	<section>
		<div class="title">후보조회</div>
		<div class="wrapper">
			<table class="viewtable" style="width: 900px">
				<tr>
					<th>후보번호</th>
					<th>성명</th>
					<th>소속정당</th>
					<th>학력</th>
					<th>주민번호</th>
					<th>지역구</th>
					<th>대표전화</th>
				</tr>
				<%for (View v : viewList) { %>
				<% %>
				<tr>
					<td><%=v.getM_no()%></td>
					<td><%=v.getM_name()%></td>
					<td><%=v.getP_name()%></td>
					<td><%=v.getP_school()%></td>
					<td><%=v.getM_jumin()%></td> 
					<td><%=v.getM_city()%></td>
					<td><%=v.getP_tel1()%></td>
				</tr>
				<%} %>
			</table>
		</div>
	</section>

	<%@ include file="footer.jsp"%>
</body>
</html>