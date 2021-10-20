package com.education.business.message;

import lombok.Data;
import java.io.Serializable;

/**
 * @author zengjintao
 * @version 1.6.3
 * @create_at 2021年9月10日 0010 11:18
 */
@Data
public abstract class QueueMessage implements Serializable {

    private String exchange;
    private String routingKey;
    /**
     * 消息唯一标识
     */
    private String messageId;
}
