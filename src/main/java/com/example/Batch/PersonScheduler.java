package com.example.Batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PersonScheduler {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job myJob;

    @Scheduled(initialDelay = 5000)
    public void runScheduledJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .addString("filePath", "src/main/resources/person_sample_1000.xlsx")
                    .toJobParameters();

            jobLauncher.run(myJob, params);
            System.out.println("Scheduled batch job executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}