var index = 0;

window.onload = function() { 
	addKeyUpEvent();
};

function solveQuestion(gameNo, quizNumber){
	console.log(index +" " + quizNumber);
	const answer = document.querySelector('#inputAnswer').value;
	
	if (answer == "") {
		alert("공백은 입력할 수 없습니다.");
		return;
	}
	
	if(index + 1 == quizNumber){
		var url = '/game/result/' + gameNo;
		sendPost(url, answer);
	}
	else if(index + 1 < quizNumber){
		$.ajax({
			url:"/game/playGame/" + gameNo + "/" + index + "/" + encodeURI(answer),
			contentType : "application/json; charset=utf-8",
			dataType:"text", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
			type:"GET",
		}).done(function(fragment){
			console.log(fragment);
			$("#playGameDiv").replaceWith(fragment);
			$("#inputAnswer").focus();
			addKeyUpEvent();
			index++;
		});
	}
	
}

function sendPost(url, param){
	var form = document.createElement('form');
	form.setAttribute('method', 'post');
	form.setAttribute('action', url);
	form.charset = 'utf-8';
	var hiddenField = document.createElement('input');
	hiddenField.setAttribute('type', 'hidden');
	hiddenField.setAttribute('name', 'answer');
	hiddenField.setAttribute('value', param);
	form.appendChild(hiddenField);
	document.body.appendChild(form);
	form.submit();
}

function getHint(memberPoint, hintPoint){
	if(memberPoint - hintPoint < 0){
		alert("포인트 부족");
		return;
	}

	// 보유하고 있는 포인트 차감해서 출력
	$("#myPoint").text("현재 " + (memberPoint - hintPoint) + "pt");

	$.ajax({
		url: "/game/getHint/" + index,
		contentType: "application/json; charset=utf-8",
		dataType: "html", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
		type: "GET",
	}).done(function(fragment) {
		console.log(fragment);
		$(".hintTableText").css("display", "");
		$(".hintBtn").attr("disabled", "true");
	});
}

// 엔터로 버튼 이벤트가 활성화되도록 설정
function addKeyUpEvent() {
	$("#inputAnswer").keyup(function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
			$("#answer_btn").click();
		}
	});
}