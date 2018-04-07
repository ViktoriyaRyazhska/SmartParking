package com.smartparking.eventprocessor.config;

import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.service.EventBatchService;
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


@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(cron = "*/10 * * * * *")
    public void perform() {
        try {
            jobLauncher.run(job, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException ex) {
            throw new IllegalStateException("Unexpected exception when executing a job.", ex);
        }
    }

    @Bean
    public Job job(@Autowired StepBuilderFactory stepBuilderFactory,
                   @Autowired JobBuilderFactory jobBuilderFactory,
                   @Autowired EventBatchService eventBatchService) {
        Step step = stepBuilderFactory.get("step")
                .<Event, Event>chunk(3)
                .reader(eventBatchService::poll)
                .writer(eventBatchService::send)
                .allowStartIfComplete(true)
                .build();
        return jobBuilderFactory.get("job").start(step).build();
    }

    @Bean
    JobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    JobRepository jobRepository(@Autowired PlatformTransactionManager transactionManager) {
        try {
            return new MapJobRepositoryFactoryBean(transactionManager).getObject();
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot create 'jobRepository' bean.", ex);
        }
    }
}
