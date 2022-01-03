package com.education.business.task;

import com.education.common.annotation.EventQueue;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021年10月16日 0016 14:13
 * @since version 1.6.5
 */
@Component
@EventQueue(name = "TestListener")
public class Test1Listener implements TaskListener{
    @Override
    public void onMessage(TaskParam taskParam) {
        System.out.println("Test1Listener");
    }
}
