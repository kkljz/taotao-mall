var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("QTU_USER_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://sso.qtu.com/user/getUser/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到淘淘！<a href='http://www.sso.com/user/logout/' class='link-logout'>[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
    alert("11111111111111111111111111");
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});