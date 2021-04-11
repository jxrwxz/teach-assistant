package jxrwxz.teachassistant.web;



import jxrwxz.teachassistant.Student;
import jxrwxz.teachassistant.Teacher;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{identity}/{id}")
@Component
public class WebSocket {

//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException, JSONException {
//        String payload=message.getPayload();
////        JSONObject jsonObject=new JSONObject(payload);
//        session.sendMessage(new TextMessage("I got this (" + payload + ")"+
//                " so I am sending it back !"));
//
//    }
    private static ConcurrentHashMap<Long, WebSocket> studentWebSocketMap=new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long, WebSocket> teacherWebSocketMap=new ConcurrentHashMap<>();
    private Session session;
    private Long userId;
    private String identity;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") Long userId,@PathParam("identity") String identity){
        if(identity.equals("student")) {
            System.out.println("第" + session.getId() +"个：userId是"+ userId);
            this.userId = userId;
            this.session = session;
            this.identity="student";
            studentWebSocketMap.put(userId,this);
        }else if(identity.equals("teacher")){
            System.out.println("第" + session.getId() +"个：userId是"+ userId);
            this.userId = userId;
            this.session = session;
            this.identity="teacher";
            teacherWebSocketMap.put(userId,this);
        }

        //            Map<String,Object> map1= new HashMap<>();
//            map1.put("onlineOrNot",1);
//            map1.put("userId",userId);
    }

    @OnError
    public void onError(Throwable error){
        System.out.println("服务器端发生了错误" + error.getMessage());
    }

    @OnClose
    public void onClose(CloseReason closeReason){
        if(identity.equals("student")){
            studentWebSocketMap.remove(this.userId);
        }else if(identity.equals("teacher")){
            teacherWebSocketMap.remove(this.userId);
        }
        System.out.println(closeReason);
    }

    @OnMessage
    public void onMessage(String message){
        try{
            System.out.println("来自客户端的消息: " + message + "客户端的userID是 ；" + userId + " 客户端的sessionId是 ：" + session.getId());
                JSONObject jsonObject = new JSONObject(message);
                String textMessage = jsonObject.getString("message");
//                String fromUserId = jsonObject.getString("fromUserId");
                String toUserId = jsonObject.getString("toUserId");
                String fromUserName=jsonObject.getString("fromUserName");

                Map<String, Object> map1 = new HashMap<>();
                map1.put("textMessage", textMessage);
                map1.put("fromUserId", userId.toString());

                //这个ID不是websocket session ID？是userId
                map1.put("toUserId", toUserId);
                map1.put("fromUserName",fromUserName);

                System.out.println("开始发消息给(UserId) " + toUserId);
                sendMessageTo(new JSONObject(map1).toString(),toUserId);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessageTo(String message,String toUserId)throws IOException{
        if(identity.equals("student")) {
            System.out.println("身份是学生");
            for (WebSocket item : teacherWebSocketMap.values()) {
                String userIdTemp=item.userId.toString();
                System.out.println("teacherWebSocketMap里的userId" + userIdTemp);
                if (userIdTemp.equals(toUserId)) {
                    item.session.getAsyncRemote().sendText(message);
                    System.out.println("已经发完消息了");
                    break;
                }
            }
        }else if(identity.equals("teacher")){
            System.out.println("身份是老师");
            for (WebSocket item : studentWebSocketMap.values()) {
                String userIdTemp=item.userId.toString();
                System.out.println("studentWebSocketMap里的userId" + userIdTemp);
                if (userIdTemp.equals(toUserId)) {
                    item.session.getAsyncRemote().sendText(message);
                    System.out.println("已经发完消息了");
                    break;
                }
            }
        }
    }

}

