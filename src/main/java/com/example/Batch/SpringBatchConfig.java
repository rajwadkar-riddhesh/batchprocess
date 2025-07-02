package com.example.Batch;

import com.example.Batch.entity.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      ItemReader<Person> reader,
                      PersonProcessor processor,
                      JdbcBatchItemWriter<Person> personDbWriter) {

        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(personDbWriter)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             Step step1) {

        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    @StepScope
    public PersonReader personReader(@Value("#{jobParameters['filePath']}") String filePath) throws IOException {
        return new PersonReader(filePath);
    }

    @Bean
    public JdbcBatchItemWriter<Person> personDbWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO details (user_id, first_name, last_name, gender, email, phone, date_of_birth, job_title) " +
                "VALUES (:userId, :firstName, :lastName, :gender, :email, :phone, :dateOfBirth, :jobTitle)");
        writer.setDataSource(dataSource);
        return writer;
    }


}
