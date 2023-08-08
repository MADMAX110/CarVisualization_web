var websocket = null;
//判断当前浏览器是否支持WebSocket  url的地址为本机ip地址+Tomcat端口号+项目名称+注解服务器端
if ('WebSocket' in window) {
	websocket = new WebSocket("ws://192.168.3.9:1111/CarVisualizationweb/websocket");
	console.log("建立连接")
}
else {
	alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function() {
	
};

websocket.onclose = function(e) {
	console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean);
	console.log(e);
}

//连接成功建立的回调方法
websocket.onopen = function() {
	websocket.send("web");
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
	console.log(event.data);
	data = eval("(" + event.data + ")");
	if (data.type == "car" && data.id == "car1") {
		document.getElementById("speed1").innerHTML = parseFloat(data.carSpeed).toFixed(2);
		document.getElementById("acceleration1").innerHTML = parseFloat(data.acceleration).toFixed(2);
		var x = parseFloat(data.x).toFixed(2);
		var y = parseFloat(data.y).toFixed(2);
		document.getElementById("x1").innerHTML = x;
		document.getElementById("y1").innerHTML = y;
		var wid = document.getElementById("map").clientWidth;
		document.getElementById("car1").style.left = parseFloat(x * wid / 40).toFixed(2) - 20 + "px";
		document.getElementById("car1").style.top = parseFloat(y * wid / 40).toFixed(2) - 20 + "px";
	}else if (data.type == "car" && data.id == "car2") {
		document.getElementById("speed2").innerHTML = parseFloat(data.carSpeed).toFixed(2);
		document.getElementById("acceleration2").innerHTML = parseFloat(data.acceleration).toFixed(2);
		var x = parseFloat(data.x).toFixed(2);
		var y = parseFloat(data.y).toFixed(2);
		document.getElementById("x2").innerHTML = x;
		document.getElementById("y2").innerHTML = y;
		var wid = document.getElementById("map").clientWidth;
		document.getElementById("car2").style.left = parseFloat(x * wid / 40).toFixed(2) - 20 + "px";
		document.getElementById("car2").style.top = parseFloat(y * wid / 40).toFixed(2) - 20 + "px";
	} 
	else if (data.type == "path") {
		document.getElementById('map').style.backgroundImage = "url(" + data.data + ")";
	}
}

//连接关闭的回调方法
websocket.onclose = function() {

}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
	closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
	websocket.close();
}

document.getElementById("map").onclick = function(e) {
	var map = document.getElementById("map");
	var wid = map.clientWidth;
	var x = parseFloat(e.offsetX * 40 / wid).toFixed(2);
	var y = parseFloat(e.offsetY * 40 / wid).toFixed(2);
	if (isvalidarea(x, y)){
		if (document.getElementById("dot"))map.removeChild(document.getElementById("dot"));
		if (document.getElementById("tishi"))document.getElementById("left").removeChild(document.getElementById("tishi"));
		document.getElementById("dest").value = "(" + x + ", " + y + ")";
		var dot = document.createElement("div");
		dot.style.backgroundColor = "red";
		dot.style.width = "10px";
		dot.style.height = "10px";
		dot.style.left = e.offsetX + "px";
		dot.style.top = e.offsetY + "px";
		dot.style.cursor = "pointer";
		dot.style.position = "absolute";
		dot.id = "dot";
		map.appendChild(dot);
	}
	else {
		if (document.getElementById("dot"))map.removeChild(document.getElementById("dot"));
		if (!document.getElementById("tishi")){
			var left = document.getElementById("left");
			var ps = document.createElement("p");
			ps.innerText = "请点击有效位置";
			ps.style.color = "red";
			ps.id = "tishi";
			left.appendChild(ps);
		}
	}
}

document.getElementById("navigation").onclick = function() {
	websocket.send("taxi:" + document.getElementById('dest').value);
}

function isvalidarea(x, y){
	if ((x >= 0.5 && x <= 14.5 && y >= 7.5 && y <= 28.5) ||
	(x >= 14.5 && x <= 18.75 && y >= 0.5 && y <= 7.5) ||
	(x >= 18.75 && x <= 20.5 && y >= 32.5 && y <= 39.5) ||
	(x >= 24.5 && x <= 28 && y >= 7.5 && y <= 32.5) ||
	(x >= 28 && x <= 32.5 && y >= 0.5 && y <= 7.5) ||
	(x >= 28 && x <= 32.5 && y >= 32.5 && y <= 39.5) ||
	(x >= 32.5 && x <= 39.5 && y >= 7.5 && y <= 32.5))return true;
	return false;
}
