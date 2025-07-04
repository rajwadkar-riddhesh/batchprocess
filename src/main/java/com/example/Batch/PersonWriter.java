package com.example.Batch;

import com.example.Batch.entity.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;

public class PersonWriter {


    public static JdbcBatchItemWriter<Person> personDbWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO details (user_id, first_name, last_name, gender, email, phone, date_of_birth, job_title) " +
                "VALUES (:userId, :firstName, :lastName, :gender, :email, :phone, :dateOfBirth, :jobTitle)");
        writer.setDataSource(dataSource);
        return writer;
    }
}
