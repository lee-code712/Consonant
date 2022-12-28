var setTime = 60;	// 최초 설정 시간(기본 : 초)

window.onload = function TimerStart(){ tid=setInterval('msg_time()', 1000) };

function msg_time() {
			
	var msg = "제한시간<br/>" + setTime;
	
	var div = document.getElementById("timer");
	div.innerHTML=msg;
					
	setTime--;
			
	if (setTime < 0) {
		clearInterval(tid);
		alert("종료"); // 종료대신 결과 화면으로 이동하도록 변경하기
	}
			
}