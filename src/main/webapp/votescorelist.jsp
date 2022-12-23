<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="DTO.*"%>
<%
request.setCharacterEncoding("UTF-8");
ArrayList<VoteScore> voteScoreList = new ArrayList<VoteScore>();
voteScoreList = (ArrayList<VoteScore>) request.getAttribute("voteScoreList");
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
		<div class="title">투표검수조회</div>
		<div class="wrapper">
			<table>
				<tr>
					<th>성명</th>
					<th>생년월일</th>
					<th>나이</th>
					<th>성별</th>
					<th>후보번호</th>
					<th>투표시간</th>
					<th>유권자확인</th>
				</tr>
				<%
				for (VoteScore v : voteScoreList) {
				%>
				<tr>
					<td><%=v.getV_name()%></td>
					<td><%=v.getV_jumin()%></td>
					<td><%=v.getAge()%></td>
					<td><%=v.getGender()%></td>
					<td><%=v.getM_no()%></td>
					<td><%=v.getV_time()%></td>
					<td><%=v.getVoter()%></td>
				</tr>
				<%
				}
				%>
			</table>
		</div>
	</section>


	<%@include file="footer.jsp"%>
</body>
</html>