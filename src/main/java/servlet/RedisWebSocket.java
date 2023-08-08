package servlet;

import java.io.IOException;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@ServerEndpoint("/rediswebsocket")
public class RedisWebSocket {
  private Session session;
  
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    
    Thread thread = new Thread(() -> {
      Jedis jedis = new Jedis("localhost", 6379);  // 连接Redis
      String channel = "mychannel";   
      
      // 订阅Redis频道
      jedis.subscribe(new JedisPubSub() { 
        @Override  
        public void onMessage(String channel, String message) {
          try {
            session.getBasicRemote().sendText(message); // 推送到客户端
          } catch (IOException e) {
            e.printStackTrace();
          } 
        }   
      }, channel);
    });
 
    thread.start();
  }
}
