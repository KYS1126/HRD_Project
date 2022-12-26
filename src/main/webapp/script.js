function chkform () {
	var f = document.frm; //form태그 전체를 가르킨다
		
	if(f.title.value == ''){
		alert("제목을 입력해주십시오.");
		return false;
	}	
	
	if(f.title.value == ''){
		alert("아이디를 입력해주십시오.");
		return false;
	}
	
	f.submit(); //form태그 전송

}

