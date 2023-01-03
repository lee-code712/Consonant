document.addEventListener("DOMContentLoaded", function() {    
	gameListJson("", "all");
});

function changeCategory(obj) {
	gameListJson("", obj);
}

function gameListJson(pageType, categoryId) {
	var url = "/game/list/";
	if (pageType == "") {
		url += categoryId;
	} else {
		url += "page/" + pageType;
	}
	
	$.ajax({
		url : url,
		type : "get",
		dataType : "json",
		success : function(data) {
			const content = document.getElementById("gameListDiv");
			content.innerHTML = "";
			
			console.log(data);
     		
     		// 게임 목록 생성
			$.each(data.pageList, function(index, game) {
				const div = document.createElement("div");
				div.className = "quizInfoBox";
				const table = document.createElement("table");
				table.className = "quizInfoTable";
				
				const gameInfoTr = document.createElement("tr");
				gameInfoTr.appendChild(createTdElement("quizNameTd", "quizNameTd", game.gameTitle));
				gameInfoTr.appendChild(createTdElement("scoreTd", "scoreTd", "내 점수: " + game.score + " / 총 점수: " + game.gameScore));
				
				const buttonTd = document.createElement("td");
				buttonTd.style.width = "15%";
				buttonTd.rowSpan = "2";
				const startBtn = document.createElement("button");
				startBtn.className = "btnGameStart";
				startBtn.type = "button";
				startBtn.innerText = "시작";
				startBtn.onclick = function(){ location.href = "/game/playGame/" + game.gameNo };
				
				buttonTd.appendChild(startBtn);
				gameInfoTr.appendChild(buttonTd);
				
				const gameInfoTr2 = document.createElement("tr");
				gameInfoTr2.appendChild(createTdElement("", "", game.gameIntro));
				
				let info = "";
				if (game.gameDifficulty == 3) {
					info += "난이도: 상";
				} else if (game.gameDifficulty == 2) {
					info += "난이도: 중";
				} else if (game.gameDifficulty == 1) {
					info += "난이도: 하";
				}
				
				gameInfoTr2.appendChild(createTdElement("", "", info + " / 문제 개수: " + game.quizNumber));
				
				table.appendChild(gameInfoTr);
				table.appendChild(gameInfoTr2);
				div.appendChild(table);
				content.appendChild(div);
			});
			
			// 페이징에 따른 이전, 다음 페이지 버튼 생성
			const pagingWrap = document.createElement("div");
			pagingWrap.id = "pagingWrap";
			pagingWrap.className = "pagingWrap";
			
			if (!data.firstPage) {
				pagingWrap.innerText = "Back";
				pagingWrap.onclick = function(){ gameListJson("previous"); };
			}
			
			if (!data.lastPage) {
				pagingWrap.innerText = "Next";
				pagingWrap.onclick = function(){ gameListJson("next"); };
			}

			content.appendChild(pagingWrap);
		},
		
		error : function() {
			alert('error');			
		}
	});
};

function createTdElement(className, id, value) {
	const td = document.createElement("td");
	
	td.className = className;
	td.id = id;
	td.innerText = value;
	
	return td;
}