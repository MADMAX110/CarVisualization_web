<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>自动驾驶数字孪生平台</title>
	<link rel="stylesheet" href="css/user.css" type="text/css" />
</head>

<body>
	
<div id="center">
	<div id="lable_1">&nbsp;<strong><a href="home.jsp">全 局 视 角</a></strong>
	</div>		
	<div id="lable_2">&nbsp;<strong><a href="car.jsp"> 小 车 视 角</a></strong>
  	</div>
	<div id="lable_3">&nbsp;<strong>用 户 打 车</strong>
  	</div>
</div>
	
<div id="lable_map">&nbsp;全局地图
	</div>	
<div id="lable_car">&nbsp;打车功能
	</div>
<div id="nav">
	</div>
	<div id="left">
		<div id="map">
			<!-- 地图显示区域 -->
			<div id="car1"></div>
			<div id="car2"></div>
			<!-- 小车显示区域 -->
		</div>
</div>

	<div id="right">
		<h3>	&nbsp;车辆信息</h3>
	<div id="car_1">	
		<table cellspacing="10" id="tab">		
			<tr><td><big><strong>id：<span id="carid">car1</span></strong></big></td>
			</tr>
			<tr><td><big><strong>速度：<span id="speed1">0</span>m/s</strong></big></td>
			</tr>
			<tr>
				<td><big><strong>加速度：<span id="acceleration1">0</span>m/s²</strong></big></td>
			</tr>
			<tr>
				<td><big><strong>横坐标：<span id="x1">0</span></big></strong></td>
			</tr>
			<tr>
				<td><big><strong>纵坐标：<span id="y1">0</span></big></strong></td>
			</tr>		
	</table>
	</div>
	<div id="car_2">	
		<table cellspacing="10" id="tab_1">
				
			<tr>
				<td><big><strong>id：<span id="carid">car2</span></strong></big></td>
			</tr>
			<tr>
				<td><big><strong>速度：<span id="speed2">0</span>m/s</strong></big></td>
			</tr>
			<tr>
				<td><big><strong>加速度：<span id="acceleration2">0</span>m/s²</strong></big></td>
			</tr>
			<tr>
				<td><big><strong>横坐标：<span id="x2">0</span></big></strong></td>
			</tr>
			<tr>
				<td><big><strong>纵坐标：<span id="y2">0</span></big></strong></td>
			</tr>		
	</table>
	</div>
	</div>
	<div id="right_1">
		<h3>&nbsp; 打车目的地</h3>
		<input class="input_destination" size="15" id="dest" value="(0, 0)"
			placeholder="(0, 0)">
		<button type="button" class="button-container_0" id ="navigation"><big>打车</big></button>
	</div>	
	<h6>
	  <script src="scripts/user.js"></script>
</h6>
</body>

</html>