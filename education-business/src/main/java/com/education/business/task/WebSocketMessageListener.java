package com.education.business.task;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.education.MessageInfoService;
import com.education.business.service.education.StudentInfoService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.webSocket.SystemWebSocketHandler;
import com.education.common.annotation.EventQueue;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.common.utils.IpUtils;
import com.education.common.utils.ResultCode;
import com.education.model.dto.SocketMessageCommand;
import com.education.model.entity.MessageInfo;
import com.education.model.entity.TestPaperInfo;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * websocket 异步发送消息通知
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/24 21:28
 */
@Component
@EventQueue(name = LocalQueueConstants.SYSTEM_MESSAGE)
@Slf4j
public class WebSocketMessageListener implements TaskListener {

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;
    @Autowired
    private TestPaperInfoService testPaperInfoService;
    @Autowired
    private MessageInfoService messageInfoService;
    @Resource
    private StudentInfoService studentInfoService;


    @Override
    public void onMessage(TaskParam taskParam) {
        try {
            Thread.sleep(5000); // 休眠5秒后在发送消息到前端
            Integer messageType = taskParam.getInt("message_type");
            String content = "";
            if (SocketMessageTypeEnum.REJECT_SESSION.getValue().equals(messageType)) {
                String ip = taskParam.getStr("ip");
                String address = IpUtils.getIpAddress(ip);
                content = "您的账号已在" + address + "登录，" +
                        "5秒后将自动下线，如非本人操作请重新登录并及时修改密码";
            } else if (SocketMessageTypeEnum.EXAM_CORRECT.getValue().equals(messageType)) {
                Integer studentId = taskParam.getInt("studentId");
                String socketSessionId = studentInfoService.getStudentSocketSessionId(studentId);
                taskParam.put("socketSessionId", socketSessionId);
                this.saveExamMessage(taskParam);
            }

            SocketMessageCommand socketMessageCommand = new SocketMessageCommand();
            socketMessageCommand.setMessageType(messageType);
            socketMessageCommand.setMsgContent(content);
            String sessionId = taskParam.getStr("sessionId");
            systemWebSocketHandler.sendMessageToPage(sessionId, JSONUtil.toJsonStr(socketMessageCommand));
        } catch (Exception e) {
            log.error("websocket消息发送异常", e);
        }
    }

    private void saveExamMessage(TaskParam taskParam) {
        TestPaperInfo testPaperInfo = testPaperInfoService.getOne(Wrappers.lambdaQuery(TestPaperInfo.class)
                .select(TestPaperInfo::getName)
                .eq(TestPaperInfo::getId, taskParam.getInt("testPaperInfoId")));

        MessageInfo messageInfo = new MessageInfo();
        String content = "您参加的考试【" + testPaperInfo.getName() + "】已被管理员批改,赶紧去查看吧!";
        messageInfo.setContent(content);
        messageInfo.setStudentId(taskParam.getInt("studentId"));
        messageInfo.setMessageType(SocketMessageTypeEnum.EXAM_CORRECT.getValue());
        messageInfo.setCreateDate(new Date());
        messageInfoService.save(messageInfo);
    }
}
