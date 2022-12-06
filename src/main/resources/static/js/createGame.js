

function updateQuizList(){
	
	var consonantData=$("#consonant").val().trim();
	var answerData=$("#answer").val().trim();
	var quizLevelData=$("#quizLevel").val();
	var quizHintData=$("#quizHint").val().trim();
	var hintPointData=$("#hintPoint").val();
	
	console.log(consonantData);
	console.log(answerData);
	console.log(quizLevelData);
	console.log(quizHintData);
	console.log(hintPointData);
	
	$.ajax({
		url:"/game/insertQuiz",
		contentType : "application/json; charset=utf-8",
		dataType:"text", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
		data: JSON.stringify({
			"question":consonantData,
			"answer":answerData,
			"quizDifficulty":quizLevelData,
			"hint":quizHintData,
			"hintPoint":hintPointData
			}),
		type:"POST",
	}).done(function(fragment){
		console.log(fragment);
		$("#resultDiv").replaceWith(fragment);
		$("#consonant").val("");
		$("#answer").val("");
		$("#quizHint").val("");
		$("#quizLevel").val("3");
		$("#hintPoint").val("10");
	});
	
}


function removeQuiz(formName){
	
	const findForm = document.getElementById(formName);
	const questionData= findForm.querySelector('#insertedQuestion').innerText; //getElementById는 document에서만 할수있음	

	$.ajax({
		url:"/game/removeQuiz",
		contentType : "application/json; charset=utf-8",
		dataType:"text", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
		data: JSON.stringify({
			"question":questionData
			}),
		type:"POST",
	}).done(function(fragment){
		console.log(fragment);
		$("#resultDiv").replaceWith(fragment);
	});

}

function createGame(){
	$("#createGameForm").submit();
}
