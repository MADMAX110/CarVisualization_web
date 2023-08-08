<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>自动驾驶数字孪生平台</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
	<form name="form1" action="Login" method="post">
		
		<h1>自动驾驶数字孪生平台</h1>
		<input class="input_1" id="username" size="15" name="username" placeholder="用户名"><br />
		<input class="input_1" id="password" type="text" size="15" name="password" placeholder="密码"><br />
		<span class="radio_box">
		<input class="input_3" type="submit" value="登录" />
		<input class="input_3" type="button" value="注册" />
		</span><br />
		<% if (request.getAttribute("error") != null) { %>
  			<p style="color: red;" >用户名或密码错误</p>
		<% } %>
		<p>&copy; 2023. All Rights Reserved</p>
	</form>
	<script type="text/javascript" src="scripts/login.js"></script>
</body>
</html>
