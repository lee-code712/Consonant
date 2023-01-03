var index = 0;
var answerArr = [];
function solveQuestion(gameNo, quizNumber){
	const answer = document.querySelector('#inputAnswer').value;
	
	if (answer == "") {
		alert("공백은 입력할 수 없습니다.");
		return;
	}
	
	if(index + 1 == quizNumber){
		answerArr.push(answer);
		console.log(answerArr);
		$(location).attr('href', '/game/result');
	}
	else if(index + 1 < quizNumber){
		$.ajax({
			url:"/game/playGame/" + gameNo + "/" + ++index + "/" + encodeURI(answer),
			contentType : "application/json; charset=utf-8",
			dataType:"text", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
			type:"GET",
		}).done(function(fragment){
			console.log(fragment);
			$("#playGameDiv").replaceWith(fragment);
			answerArr.push(answer);
			console.log(answerArr);
		});
	}
	
}

function getHint(){
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