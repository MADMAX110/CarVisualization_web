<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自动驾驶数字孪生平台</title>
</head>
<link rel="stylesheet" href="css/car.css" type="text/css" />

<body>
	<div id="center">
		<div id="lable_1">&nbsp;<strong><a href="home.jsp"> 全 局 视 角 </a> </strong>
		</div>	
		<div id="lable_2">&nbsp;<strong>小 车 视 角</strong>
		</div>
		<div id="lable_3">&nbsp;<strong><a href="user.jsp"> 用 户 打 车</a></strong>
		</div>
	</div>
	<div id="lable_map">&nbsp;全局地图</div>
	<div id="lable_carsense">&nbsp;小车感知</div>
	<div id="lable_carstate">&nbsp;小车状态</div>
	<div id="nav">
		<select id="carlist" name="carlist">
			<option value="car1">car1</option>
			<option value="car2">car2</option>
		</select>
	</div>
	<div id="lable_cardestination">&nbsp;小车行驶</div>
	<div id="left">
		<div id="map">
			<!-- 地图显示区域 -->
			<div id="car"></div>
			<!-- 小车显示区域 -->
		</div>
	</div>

	<div id="right">
		<h3>&nbsp;小车视角</h3>
		<img onclick="getCarview()" id="carview" />
		<h3>&nbsp;雷达图</h3>
		<img id="lidarview" />
	</div>

	<div id="right_1">

		<table cellspacing="10" id="tab">
			<td>速度：<span id="speed">0</span>m/s
			</td>
			<tr>
				<td>加速度：<span id="acceleration">0</span>m/s²</td>
			</tr>
			<tr>
				<td>横坐标：<span id="x">0</span></td>
			</tr>
			<tr>
				<td>纵坐标：<span id="y">0</span></td>
			</tr>
		</table>
	</div>

	<div id="right_2">
		<input class="input_destination" size="10" id="dest" value="(0, 0)"
			placeholder="(0, 0)">
		<button type="button" class="button-container_0" id="navigation">
			<big>导航</big>
		</button>
		<br />
		<button type="button" class="button-container_1" id="straight">
			<big>直行</big>
		</button>
		<button type="button" class="button-container_2" id="turn">
			<big>掉头</big>
		</button>
		<button type="button" class="button-container_3" id="turnleft">
			<big>左转</big>
		</button>
		<button type="button" class="button-container_4" id="turnright">
			<big>右转</big>
		</button>
		<button type="button" class="button-container_5" id="stop">
			<big>停车</big>
		</button>
	</div>
	<script src="scripts/home.js"></script>
</body>
</html>
