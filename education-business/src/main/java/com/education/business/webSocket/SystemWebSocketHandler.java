package com.education.business.webSocket;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.education.business.service.education.StudentInfoService;
import com.education.business.service.system.SystemAdminService;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.SocketMessageCommand;
import com.jfinal.kit.HashKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * webSocket 通讯
 * @author taoge
 * @version 1.0.2
 * @update  1.0.4
 * @create_at 2018年11月18日下午5:24:06
 */
@Component
@Slf4j
public class SystemWebSocketHandler implements WebSocketHandler {

    @Resource
    private StudentInfoService studentInfoService;
    @Resource
    private SystemAdminService systemAdminService;

    private static final Map<String, String> WEBSOCKET_SESSION_TOKEN_MAPPING = new HashMap<>();
    private static final Map<String, WebSocketSession> WEB_SOCKET_SESSION = new HashMap<>();

    // 缓存websocket 会话id 与用户会话的id 映射关系
  //  private static final String WEBSOCKET_SESSION_TOKEN_MAPPING = "websocket:session:token:mapping";

    // websocket 通讯缓存
   // private static final String WEB_SOCKET_SESSION = "websocket:session";

    /**
     * 连接 就绪时
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("-------------------------- WebSocket Connection Success ---------------------------");
    }

    /**
     * 处理消息
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String message = String.valueOf(webSocketMessage.getPayload());
        log.info("Socket Listener Message:{}", message);
        SocketMessageCommand socketMessageCommand = JSONUtil.toBean(message, SocketMessageCommand.class);
        Integer messageType = socketMessageCommand.getMessageType();
        Integer userId = socketMessageCommand.getUserId();
        if (!SocketMessageTypeEnum.contains(messageType)) {
            log.info("错误消息类型:{}", socketMessageCommand.getMessageType());
            return;
        }

        String token = socketMessageCommand.getToken();
        if (StrUtil.isBlank(token)) {
            return;
        }

        String md5Token = HashKit.md5(socketMessageCommand.getToken());
        // socket 连接成功时接收消息
        if (SocketMessageTypeEnum.STUDENT_CONNECTION_SUCCESS.getValue().equals(messageType)) {
            studentInfoService.updateSocketSessionId(userId, md5Token);
        } else if (SocketMessageTypeEnum.ADMIN_CONNECTION_SUCCESS.getValue().equals(messageType)) {
            systemAdminService.updateSocketSessionId(userId, md5Token);
        }

        WEBSOCKET_SESSION_TOKEN_MAPPING.put(webSocketSession.getId(), md5Token);
        WEB_SOCKET_SESSION.put(md5Token, webSocketSession);

      //  redisTemplate.boundHashOps(WEBSOCKET_SESSION_TOKEN_MAPPING).put(webSocketSession.getId(), md5Token);
        // 管理后台socket 连接成功
       // BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(WEB_SOCKET_SESSION);
      //  boundHashOperations.put(md5Token, webSocketSession);
    }

    /**
     * 发送消息到页面
     * @param md5Token
     * @param message
     */
    public void sendMessageToPage(String md5Token, String message) {
     //   WebSocketSession webSocketSession = (WebSocketSession) redisTemplate.boundHashOps(WEB_SOCKET_SESSION)
     //           .get(md5Token);
        WebSocketSession webSocketSession = WEB_SOCKET_SESSION.get(md5Token);
        if (ObjectUtils.isNotEmpty(webSocketSession)) {
            try {
                log.info("Socket Server Push Message:{}", message);
                webSocketSession.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("webSocket 消息发送异常", e);
            }
        }
    }

    /**
     * 处理传输时异常
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession != null) {
            webSocketSession.close();
        }
        log.info("-------------------------- WebSocket Connection Error ---------------------------");
    }

    /**
     * 关闭 连接时
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        String sessionId = webSocketSession.getId();
        String mdkToken = WEBSOCKET_SESSION_TOKEN_MAPPING.get(sessionId);


        WEBSOCKET_SESSION_TOKEN_MAPPING.remove(sessionId);
        WEB_SOCKET_SESSION.remove(mdkToken);

     /*   String mdkToken = (String) redisTemplate.boundHashOps(WEBSOCKET_SESSION_TOKEN_MAPPING)
                .get(sessionId);
        // 删除socket 会话缓存
        redisTemplate.boundHashOps(WEBSOCKET_SESSION_TOKEN_MAPPING).delete(sessionId);
        redisTemplate.boundHashOps(WEB_SOCKET_SESSION).delete(mdkToken);*/
        webSocketSession.close();
        log.info("-------------------------- WebSocket Close Success ---------------------------");
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
