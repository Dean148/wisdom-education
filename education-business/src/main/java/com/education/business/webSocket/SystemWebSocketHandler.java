package com.education.business.webSocket;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.education.business.service.education.StudentInfoService;
import com.education.business.service.system.SystemAdminService;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.common.model.JwtToken;
import com.education.common.utils.IpUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.RequestUtils;
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
    @Resource
    private JwtToken jwtToken;

    private static final Map<String, String> WEBSOCKET_SESSION_TOKEN_MAPPING = new HashMap<>();
    private static final Map<String, WebSocketSession> WEB_SOCKET_SESSION = new HashMap<>();

    /**
     * 连接 就绪时
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

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
        SocketMessageCommand socketMessageCommand;
        try {
            socketMessageCommand = JSONUtil.toBean(message, SocketMessageCommand.class);
        } catch (Exception e) {
            log.warn("socket 通讯消息格式:{}错误", message);
            return;
        }
        Integer messageType = socketMessageCommand.getMessageType();
        String socketSessionId = webSocketSession.getId();
        if (!SocketMessageTypeEnum.contains(messageType)) {
            log.error("错误消息类型:{}", socketMessageCommand.getMessageType());
            return;
        }

        if (SocketMessageTypeEnum.HEART.getCode().equals(messageType)) {
            log.info("ws心跳包:{}", message);
            this.sendMessage(webSocketSession, message);
            return;
        }


        // 连接成功时消息推送
        if (SocketMessageTypeEnum.isConnectionSuccess(messageType)) {
            String token = socketMessageCommand.getToken();
            if (StrUtil.isBlank(token)) {
                return;
            }

            String value = jwtToken.parseTokenToString(token);
            // 非法token 移除会话session
            if (StrUtil.isBlank(value)) {
                String sessionKey = WEBSOCKET_SESSION_TOKEN_MAPPING.get(socketSessionId);
                WEBSOCKET_SESSION_TOKEN_MAPPING.remove(socketSessionId);
                WEB_SOCKET_SESSION.remove(sessionKey);
                return;
            }

            if (WEB_SOCKET_SESSION.containsKey(socketSessionId)) {
                log.error("socket session:{}已存在", socketSessionId);
                return;
            }
            Integer userId = socketMessageCommand.getUserId();
            String md5Token = HashKit.md5(token);
            // socket 连接成功时接收消息
            if (SocketMessageTypeEnum.STUDENT_CONNECTION_SUCCESS.getCode().equals(messageType)) {
                studentInfoService.updateSocketSessionId(userId, md5Token);
            } else if (SocketMessageTypeEnum.ADMIN_CONNECTION_SUCCESS.getCode().equals(messageType)) {
                systemAdminService.updateSocketSessionId(userId, md5Token);
            }
            WEBSOCKET_SESSION_TOKEN_MAPPING.put(socketSessionId, md5Token);
            WEB_SOCKET_SESSION.put(md5Token, webSocketSession);
            log.info("-------------------------- WebSocket Connection Success ---------------------------");
        }
    }

    /**
     * 发送消息到页面
     * @param md5Token
     * @param message
     */
    public void sendMessageToPage(String md5Token, String message) {
        WebSocketSession webSocketSession = WEB_SOCKET_SESSION.get(md5Token);
        if (ObjectUtils.isNotEmpty(webSocketSession)) {
            this.sendMessage(webSocketSession, message);
        }
    }

    private void sendMessage(WebSocketSession webSocketSession, String message) {
        try {
            webSocketSession.sendMessage(new TextMessage(message));
            log.info("Socket Server Push SocketSessionId:{} Message:{} Success", webSocketSession.getId(), message);
        } catch (IOException e) {
            log.error("webSocket 消息发送异常", e);
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
        log.warn("-------------------------- WebSocket Connection Error ---------------------------");
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
        webSocketSession.close();
        log.info("-------------------------- WebSocket Close Success ---------------------------");
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
