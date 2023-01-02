window.onload = function() {
	// 맞춘 개수 출력
	let correctCount = 0;
	
	let keys = Object.keys(results);
	for (i = 0; i < keys.length; i++) {
		if (results[keys[i]] != "X") {
			correctCount++;
		}
	}
	
	const viewCount = document.getElementById("viewCount");
	viewCount.innerText = correctCount + viewCount.innerText;
	
	// 획득 포인트 출력
	let getPoint = Math.floor(correctCount / 10) * 10;
	const viewPoint = document.getElementById("viewPoint");
	viewPoint.innerText = "+" + getPoint;
};

function viewDesc (key) {
	// 이미 설명이 보이는 상태면 설명을 지움
	const descEl = document.getElementById(key + "_desc");
	
	if (descEl != null && descEl != undefined) {
		hideDesc(key);
		return;
	}
	
	// results[key] 값이 X이면 함수 종료
	const result = results[key];
	if (result == "X") {
		return;
	}
	
	// 설명이 보이지 않으면 설명을 화면에 띄움
	const selector = document.getElementById(key);
	const duplicateList = JSON.parse(results[key])["channel"].item;
	
	let viewText = "";
	duplicateList.forEach(function(duplicate) {
		viewText += "[" + duplicate.pos + "]" + " " + duplicate.sense.definition + "\n\n";
	});
	
	let p = document.createElement("p");
	p.id = key + "_desc";
	p.className = "desc";
	p.innerText = viewText;
	selector.insertAdjacentElement('afterend', p);
}

function hideDesc (key) {
	const selector = document.getElementById(key + "_desc");
	selector.remove();
}