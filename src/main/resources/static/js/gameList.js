function changeCategory(obj){
	$.ajax({
		url:"/game/category/gameList",
		contentType : "application/json; charset=utf-8",
		dataType:"text", //반환 타입->데이터타입이랑 서비스에서 반환하는 타입이 안맞으면 done 동작 안함
		data:  {categoryId: obj},
		type:"GET",
		 error:function(request,status,error){
        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       }
	}).done(function(fragment){
		console.log(fragment);
		$("#gameListDiv").replaceWith(fragment);
	});
	
}
