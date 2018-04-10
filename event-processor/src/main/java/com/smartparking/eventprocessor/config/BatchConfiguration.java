package com.smartparking.eventprocessor.config;

import com.smartparking.eventprocessor.config.properties.BatchProperties;
import com.smartparking.eventprocessor.model.view.UnverifiedEvent;
import com.smartparking.eventprocessor.model.view.VerifiedEvent;
import com.smartparking.eventprocessor.service.EventBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    private BatchProperties batchProperties;
    @Autowired
    private EventBatchService eventBatchService;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job verifiedEventJob;

    @Autowired
    private Job unverifiedEventJob;

    @Scheduled(fixedRate = 1000)
    public void launchVerifiedEventJob() {
        try {
            jobLauncher.run(verifiedEventJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
            log.error("Unexpected exception when executing a job. ", ex);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void launchUnverifiedEventJob() {
        try {
            //TODO BUG
            jobLauncher.run(unverifiedEventJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
            log.error("Unexpected exception when executing a job. ", ex);
        }
    }

    @Bean
    public Job verifiedEventJob() {
        Step step = stepBuilderFactory.get("verifiedEventStep")
                .<VerifiedEvent, VerifiedEvent>chunk(batchProperties.getChunkSize())
                .reader(eventBatchService::pollVerified)
                .writer(eventBatchService::sendVerified)
                .allowStartIfComplete(true)
                .build();
        return jobBuilderFactory.get("verifiedEventJob").start(step).build();
    }

    @Bean
    public Job unverifiedEventJob() {
        Step step = stepBuilderFactory.get("unverifiedEventStep")
                .<UnverifiedEvent, UnverifiedEvent>chunk(batchProperties.getChunkSize())
                .reader(eventBatchService::pollUnverified)
                .writer(eventBatchService::sendUnverified)
                .allowStartIfComplete(true)
                .build();
        return jobBuilderFactory.get("unverifiedEventJob").start(step).build();
    }
}
