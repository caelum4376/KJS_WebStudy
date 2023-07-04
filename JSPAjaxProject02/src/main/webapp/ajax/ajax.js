const xmlHttpRequest = new XMLHttpRequest();
const mehtod = "GET"
const url = "my.jsp"
/*
	XMLHttpRequest : 브라우저 내장객체
	-------------- 전송 (요청) , 결과값을 받는다
	1) 연결 : open
	2) 데이터 전송 : send()
	3) 결곽밧을 받아서 처리
		readyState, status
		----------  ------
		준비과정	상태 => 200(정상)
		0, 1, 2, 3, 4 => send()완료
		if (xmlHttpRequest.readyState==4 && xmlHttpRequest.status == 200) {
			데이터를 읽어서 출력
		}
		-------------------------------------------------------------------
		success:function()
*/
function ajaxConfig(method, url, callback) {
	xmlHttpRequest.open(method, url, true); // true => 비동기
	xmlHttpRequest.onreadystatechange = callback;
	xmlHttpRequest.send(null);	
}
 
/*
		$.ajax({
			type:
			url:
			data:{}
			success:function(*)
		})
*/