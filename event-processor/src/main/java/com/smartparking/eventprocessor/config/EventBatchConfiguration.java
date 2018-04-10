package com.smartparking.eventprocessor.config;

import com.smartparking.eventprocessor.config.properties.EventBatchProperties;
import com.smartparking.eventprocessor.model.view.Event;
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
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableScheduling
@EnableBatchProcessing
public class EventBatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    private EventBatchProperties eventBatchProperties;

    @Autowired
    private EventBatchService eventBatchService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job eventSendJob;

    @Scheduled(cron = "*/1 * * * * *")
    public void launchVerifiedEventJob() {
        try {
            jobLauncher.run(eventSendJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
            log.error("Unexpected exception when executing a job. ", ex);
        }
    }

    @Bean
    public Job eventSendJob() {
        Step step = stepBuilderFactory.get("eventSendStep")
                .<Event, Event>chunk(eventBatchProperties.getChunkSize())
                .reader(eventBatchService::poll)
                .writer(eventBatchService::send)
                .allowStartIfComplete(true)
                .build();
        return jobBuilderFactory.get("eventSendJob").flow(step).build().build();
    }

    @Bean
    JobLauncher jobLauncher(@Autowired JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    JobRepository jobRepository(@Autowired PlatformTransactionManager transactionManager) throws Exception {
        return new MapJobRepositoryFactoryBean(transactionManager).getObject();
    }
}
