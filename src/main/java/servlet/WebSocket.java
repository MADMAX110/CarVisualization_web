package servlet;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import redis.clients.jedis.Jedis;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

@ServerEndpoint("/websocket")
public class WebSocket {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocket> webSet = new CopyOnWriteArraySet<WebSocket>();
    private static CopyOnWriteArraySet<WebSocket> roadSet = new CopyOnWriteArraySet<WebSocket>();
    private static HashMap<String, WebSocket> carMap = new HashMap<String, WebSocket>();
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws Exception 
     */
    @OnOpen
    public void onOpen(Session session) throws Exception{
    	this.session = session;
        System.out.println("有新连接加入！当前在线小车数为" + carMap.size());
    }
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        if(webSet.contains(this))webSet.remove(this);
        else if (carMap.containsValue(this)) {
        	for (Map.Entry<String, WebSocket> entry : carMap.entrySet()) {
        		if (entry.getValue().equals(this)) {
        			carMap.remove(entry.getKey());
        			break;
        		}
        	}
        }
        else if (roadSet.contains(this))roadSet.remove(this);
        System.out.println("当前在线小车数为" + carMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     * @throws IOException 
     */
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException{
        System.out.println("来自客户端的消息:" + message);
        if (message.equals("ping")) {
        	sendPong();
        }
        else if (message.equals("web")) {
        	webSet.add(this);
        }
        else if (message.equals("road")) {
        	roadSet.add(this);
        }
    	else if (message.startsWith("car:")) {
    		carMap.put(message.subSequence(4, message.length()).toString(), this);
    		/*
    		for(WebSocket item: webSet){
                try {
                    item.sendMessage(getCarSet().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            */
    	}
    	else if (message.startsWith("get:")) {
    		String carid = message.subSequence(4, message.length()).toString();
    		sendMessageToCar(carid, "get");
    	}
    	else if (message.startsWith("set:")) {
    		String carid = message.subSequence(4, 8).toString();
    		sendMessageToCar(carid, "set:" + message.subSequence(9, message.length()).toString());
    	}else if (message.startsWith("taxi:")) {
    		sendMessageToCar("car1", "set:" + message.subSequence(5, message.length()).toString());
    		sendMessageToCar("car2", "set:" + message.subSequence(5, message.length()).toString());
    	}
    	else if (message.startsWith("gostraight:")) {
    		String carid = message.subSequence(11, message.length()).toString();
    		sendMessageToCar(carid, "gostraight");
    	}
    	else if (message.startsWith("goleft:")) {
    		String carid = message.subSequence(7, message.length()).toString();
    		sendMessageToCar(carid, "turnleft");
    	}
    	else if (message.startsWith("goright:")) {
    		String carid = message.subSequence(8, message.length()).toString();
    		sendMessageToCar(carid, "turnright");
    	}
    	else if (message.startsWith("turnround:")) {
    		String carid = message.subSequence(10, message.length()).toString();
    		sendMessageToCar(carid, "turnover");
    	}
    	else if (message.startsWith("gostop:")) {
    		String carid = message.subSequence(7, message.length()).toString();
    		sendMessageToCar(carid, "stop");
    	}
    	else if (message.equals("getroadview")) {
    		sendMessageToRoad("getroadview");
    	}
        else {
        	for(WebSocket item: webSet){
                try {
                    item.sendMessage((String)message);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
    
    

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }


    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    
    /**
     * 向特定小车发送指令
     * @param carid
     * @param message
     * @throws IOException
     */
    public void sendMessageToCar(String carid, String message) throws IOException{
    	if (carMap.get(carid) != null)carMap.get(carid).sendMessage(message);
    }
    
    /**
     * 向路基设备发送指令
     * @param carid
     * @param message
     * @throws IOException
     */
    public void sendMessageToRoad(String message) {
    	for(WebSocket item: roadSet){
			try {
				item.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
    
    /**
     * 发送心跳回应
     */
    public void sendPong(){
    	try {
    		session.getBasicRemote().sendPong(ByteBuffer.allocate(0));
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 获取小车集合
     * @return
     */
    public Collection<String> getCarSet(){
    	return carMap.keySet();
    }
}
