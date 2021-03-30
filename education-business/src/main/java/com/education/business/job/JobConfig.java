package com.education.business.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    private static final String DEFAULT_GROUP_JOB = "default_job";

    @Bean
    public JobDetail examCountJob() {
        return JobBuilder.newJob(ExamCountJob.class)
                .withIdentity(ExamCountJob.class.getSimpleName(), DEFAULT_GROUP_JOB)
                .storeDurably().build();
    }

    @Bean
    public Trigger uploadTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(examCountJob().getKey())
                // .startAt(new Date(System.currentTimeMillis() + 1000 * 60))
                .withIdentity(ExamCountJob.class.getSimpleName())
                .withSchedule(scheduleBuilder)
                .build();
    }

}
