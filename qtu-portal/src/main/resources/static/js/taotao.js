var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("QTU_USER_TOKEN");
		// alert(_ticket);
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://sso.qtu.com/user/getUser/" + _ticket,
			dataType : "jsonp",
            // jsonp: "callback",
            // jsonpCallback:"token",
			type : "GET",
			success : function(data){
				// alert(data);
				// alert(data.status == 200);
				if(data.status == 200){
					var username = data.data.username;
					// alert(data.data);
                    // alert("username"+username);
					var html = username + "，欢迎来到淘淘！<a href='http://sso.qtu.com/user/logout/' class='link-logout'>[退出]</a>";
					$("#loginbar").html(html);
				}
			},
			error : function (data) {
				alert("请求失败");
            }
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	 TT.checkLogin();
});