package com.education.business.task;

import com.education.common.annotation.EventQueue;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021年10月16日 0016 14:12
 * @since version 1.6.5
 */
@Component
@EventQueue(name = "TestListener")
public class TestListener implements TaskListener{
    @Override
    public void onMessage(TaskParam taskParam) {
        System.out.println("TestListener");

    }
}
