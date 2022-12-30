window.onload = function() {

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