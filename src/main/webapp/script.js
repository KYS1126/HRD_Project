function  fn_submit() {
	var fn = document.frm;
	
	if(fn.jumin.value == "") {
		alert("주민등록 번호가 입력되지 않았습니다.")
		fn.jumin.focus();
		return false;
	}
	
		if(fn.username.value == "") {
		alert("성명이 입력되지 않았습니다.")
		fn.username.focus();
		return false;
	}
		if(fn.votetime.value == "") {
		alert("투표시간이 입력되지 않았습니다..")
		fn.votetime.focus();
		return false;
	}
		if(fn.votenum.value == "") {
		alert("투표장소가 입력되지 않았습니다.")
		fn.votenum.focus();
		return false;
	}
		if(fn.radio1.value == "") {
		alert("유권자 확인 번호가 입력되지 않았습니다.")
		fn.radio1.focus();
		return false;
	}
	
	fn.submit(); //컨트롤러(서버)에 전송
	
}
	function fn_reset() {
		var fn = document.frm;
		alert("정보를 지우고 처음부터 다시 입력합니다!")
		fn.jumin.focus();
		return false;
	}
	
	
	
	
	