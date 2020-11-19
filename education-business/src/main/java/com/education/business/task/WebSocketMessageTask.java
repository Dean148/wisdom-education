package com.education.business.task;

import com.alibaba.fastjson.JSONObject;
import com.education.business.webSocket.SystemWebSocketHandler;
import com.education.common.utils.IpUtils;
import com.education.common.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * websocket 异步发送消息通知
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/24 21:28
 */
@Component
@Slf4j
public class WebSocketMessageTask implements TaskListener {

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;

    @Override
    public void onMessage(TaskParam taskParam) {
        try {
            Thread.sleep(15000); // 休眠15秒后在发送消息到前端
            String address = IpUtils.getIpAddress(taskParam.getStr("ip"));
            ResultCode message = new ResultCode(ResultCode.FAIL, "您的账号已在" + address + "登录，" +
                    "5秒后将自动下线，如非本人操作请重新登录并及时修改密码");
            String sessionId = taskParam.getStr("sessionId");
            systemWebSocketHandler.sendMessageToPage(sessionId, JSONObject.toJSONString(message));
        } catch (Exception e) {
            log.error("websocket消息发送异常");
        }
    }
}
