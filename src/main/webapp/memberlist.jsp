<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="DTO.MemberList"%>
<%
request.setCharacterEncoding("UTF-8");
ArrayList<MemberList> memberList = new ArrayList<MemberList>();
memberList = (ArrayList<MemberList>) request.getAttribute("MemberList");
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
	<section>
		<div class="title">후보자 등수</div>
		<div class="warpper">
			<table>
			<tr class ="scoretable">
				<th>후보번호</th>
				<th>성명</th>
				<th>총투표건수</th>
			</tr>
			<%for (MemberList m : memberList) {%>
			<tr class ="scoretable">
				<td><%=m.getM_no()%></td>
				<td><%=m.getM_name()%></td>
				<td><%=m.getVotenum()%></td>
			</tr>
			<%} %>
			</table>
		</div>
	</section>
	<%@include file="footer.jsp"%>
</body>
</html>