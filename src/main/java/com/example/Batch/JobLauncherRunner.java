package com.example.Batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job myJob;

    @Autowired
    public JobLauncherRunner(JobLauncher jobLauncher, Job myJob) {
        this.jobLauncher = jobLauncher;
        this.myJob = myJob;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Launching batch job...");

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis()) // ensures uniqueness
                .addString("filePath", "src/main/resources/person_sample_1000.xlsx")
                .toJobParameters();

        JobExecution execution = jobLauncher.run(myJob, params);

        System.out.println("Job Execution Status: " + execution.getStatus());
    }
}
