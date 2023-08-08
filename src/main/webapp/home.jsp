<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>自动驾驶数字孪生平台</title>
<link rel="stylesheet" href="css/home.css" type="text/css" />
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<body>
	<div id="center">
		<div id="lable_1">&nbsp; <strong>全 局 视 角</strong>
		</div>		
		<div id="lable_2">&nbsp;<strong><a href="car.jsp"> 小 车 视 角</a></strong>
		</div>
		<div id="lable_3">&nbsp;<strong><a href="user.jsp"> 用 户 打 车</a></strong>
		</div>
	</div>

	<div id="lable_map">&nbsp;全局地图
		</div>

	<div id="lable_road">&nbsp;路基设备
		</div>
	<div id="nav">
		<select id="carlist" name = "carlist">
			<option>car1</option>
			<option>car2</option>
		</select>
	</div>
	<div id="left">
		<div id="map">
			<!-- 地图显示区域 -->
			<div id="car"></div>
				<!-- 小车显示区域 -->
		</div>
	</div>

	<div id="right">
		<h3>	&nbsp;路基视角</h3>
		<img onclick = "getRoadview()" id="roadview"/>
		<div id="status">
			<table cellspacing="10">
				<tr>
					<td>红绿灯状态：<span id="light">default</span></td>
					<td>路段1：<span id="road">default</span></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="right_1">
		<h3>&nbsp;停车位信息</h3>
		<div id="carstop_1">
		</div>
		<div id="carstop_2">
		</div>
		<div id="carstop_3">
		</div>
		<div id="carstop_4">
		</div>
	</div>	
	<h6>
		 <script src="scripts/home.js"></script>
	</h6>
</body>

</html>