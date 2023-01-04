var index = 0;
let currentPoint = point;
function solveQuestion(gameNo, quizNumber){
	console.log(index +" " + quizNumber);
	const answer = document.querySelector('#inputAnswer').value;
	
	if (answer == "") {
		alert("공백은 입력할 수 없습니다.");
		return;
	}
	
	if(index + 1 == quizNumber){
		var url = '/game/result';
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
			index++;
		});
	}
	
}

function sendPost(url, param){
			var form = document.createElement('form');
			form.setAttribute('method', 'post');
			form.setAttribute('action', url);
			document.charset='utf-8';
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
		alert("포인트 부족")
	}
	else{
		// 보유하고 있는 포인트 차감해서 출력
		currentPoint = currentPoint - hintPoint;
		$("#myPoint").text("현재 " + currentPoint + "pt");
		
		$.ajax({
			url:"/game/getHint/" + index,
			contentType : "application/json; charset=utf-8",
			dataType:"html", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
			type:"GET",
		}).done(function(fragment){
			console.log(fragment);
			 $(".hintTableText").css("display","");
			 $(".hintBtn").attr("disabled", "true");
		});
	}
}