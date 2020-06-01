<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<jsp:include page="/WEB-INF/commons/common-js.jsp"></jsp:include>

<script type="text/javascript">
	$(function () {
		$("#login").click(function(){
			var username = $("[name=username]").val();
			var password = $("[name=password]").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/user/login",
				type:"post",
				data:{"username":username,"password":password},
				dataType:"json",
				success:function(data){
					if (data.code==200) {
						window.location.href="${pageContext.request.contextPath}/index";
					}else {
					    alert("用户名或密码错误！")
					}
				},
				error:function (data) {
					alert("请求失败");
				}
			})
		});
	});

</script>
</head>
<body style="background-color: #F3F3F3">
    <div class="easyui-dialog" title="管理员登录" data-options="closable:false,draggable:false" style="width:400px;height:300px;padding:10px;">
       	<div style="margin-left: 50px;margin-top: 50px;">
       		<div style="margin-bottom:20px;">
	            <div>
	            	用户名: <input name="username" class="easyui-textbox" data-options="required:true" style="width:200px;height:32px"/>
	            </div>
	        </div>
	        <div style="margin-bottom:20px">
	            <div>
	            	密&nbsp;&nbsp;码: <input name="password" class="easyui-textbox" type="password" style="width:200px;height:32px" data-options=""/>
	            </div>
	        </div>
	        <div>
	            <a id="login" class="easyui-linkbutton" iconCls="icon-ok" style="width:100px;height:32px;margin-left: 50px">登录</a>
	        </div>
       	</div>
    </div>
</body>
</html>