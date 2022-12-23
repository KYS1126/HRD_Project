<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style.css">
<script type="text/javascript" src="script.js"></script>
</head>
<body>
	<%@include file="topmenu.jsp"%>
	<section>
		<div class="title">투표하기</div>
		<form name="frm" action="voteaddlist">
			<div class="wrapper">
				<table>
					<tr>
						<td>주민번호</td>
						<th><input class="addinput" type="text" name="jumin"><label>예:
								8906153154726</label></th>
					</tr>
					<tr>
						<td>성명</td>
						<th><input class="addinput" type="text" name="username"></th>
					</tr>
					<tr>
						<td>투표번호</td>
						<th>
							<!-- submit을 하면 form의 액션 값이 컨드롤러에 넘어감 --> <!-- 선택하는 순간 벨류값이 name 옆에 붙는다 -->
							<select class="selectadd" name="userselect">
								<option value="0" selected disabled hidden></option>
								<option value="1">[1] 김후보</option>
								<option value="2">[2] 이후보</option>
								<option value="3">[3] 박후보</option>
								<option value="4">[4] 조후보</option>
								<option value="5">[5] 최후보</option>
						</select>
						</th>
					</tr>
					<tr>
						<td>투표시간</td>
						<th><input class="addinput" type="text" name="votetime"></th>
					</tr>
					<tr>
						<td>투표장소</td>
						<th><input class="addinput" type="text" name="votenum"></th>
					</tr>
					<tr>
						<td>유권자확인</td>
						<th class="addth">
						<input class="addinput" type="radio" name="selectradio" value="Y">확인 
						<input class="addinput" type="radio" name="selectradio" value="N">미확인
						</th>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn" type="submit" onclick="fn_submit(); return false;">등록</button>
							<button class="btn" type="reset" onclick="fn_reset()">다시하기</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</section>
	<%@ include file="footer.jsp"%>


</body>
</html>