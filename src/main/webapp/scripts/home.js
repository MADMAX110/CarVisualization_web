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
	websocket.send("web")
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
	console.log(event.data);
	if (event.data.charAt(0) == '['){
		var data = event.data.slice(1, event.data.length - 1).split(", ");
		var sel = document.getElementById('carlist');
		while (sel.options.length > 0)sel.remove(0);		
		for (var i = 0; i < data.length; i++){
			let opt = document.createElement('option');
			opt.text = data[i];
			opt.value = data[i];
			sel.appendChild(opt);
		}
	} else {
		data = eval("(" + event.data + ")");
		if (data.type == "car" && data.id == document.getElementById('carlist').value) {
			document.getElementById("speed").innerHTML = parseFloat(data.carSpeed).toFixed(2);
			document.getElementById("acceleration").innerHTML = parseFloat(data.acceleration).toFixed(2);
			var x = parseFloat(data.x).toFixed(2);
			var y = parseFloat(data.y).toFixed(2);
			document.getElementById("x").innerHTML = x;
			document.getElementById("y").innerHTML = y;
			var wid = document.getElementById("map").clientWidth;
			document.getElementById("car").style.left = parseFloat(x * wid / 40).toFixed(2) - 20 + "px";
			document.getElementById("car").style.top = parseFloat(y * wid / 40).toFixed(2) - 20 + "px";
		} else if (data.type == "road") {
			document.getElementById("light").innerHTML = data.trafficLight;
			document.getElementById("road").innerHTML = data.roadCondition;
		} else if (data.type == "roadview") {
			document.getElementById('roadview').src = data.data;
		} else if (data.type == "carview" && data.id == document.getElementById('carlist').value) {
			document.getElementById('carview').src = data.data;
		}else if (data.type == "path" && data.id == document.getElementById('carlist').value) {
			document.getElementById('map').style.backgroundImage = "url(" + data.data + ")";
		}else if (data.type == "lidarview" && data.id == document.getElementById('carlist').value) {
			document.getElementById('lidarview').src = data.data;
		}
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

function getCarview() {
	var message = "get:" + document.getElementById('carlist').value;
	websocket.send(message);
}

function getRoadview() {
	websocket.send("getroadview");
}

function getLidarview() {
	websocket.send("getlidarview");
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
	websocket.send("set:" + document.getElementById('carlist').value + ":" + document.getElementById('dest').value);
}

document.getElementById("straight").onclick = function() {
	websocket.send("gostraight:" + document.getElementById('carlist').value);
}

document.getElementById("turn").onclick = function() {
	websocket.send("turnround:" + document.getElementById('carlist').value);
}

document.getElementById("turnleft").onclick = function(){
	websocket.send("goleft:" + document.getElementById('carlist').value);
}

document.getElementById("turnright").onclick = function(){
	websocket.send("goright:" + document.getElementById('carlist').value);
}

document.getElementById("stop").onclick = function(){
	websocket.send("gostop:" + document.getElementById('carlist').value);
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
