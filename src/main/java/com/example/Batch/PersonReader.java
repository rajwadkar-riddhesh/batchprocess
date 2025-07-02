package com.example.Batch;

import com.example.Batch.entity.Person;
import jakarta.annotation.PreDestroy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class PersonReader implements ItemReader<Person> {

    private final Iterator<Row> rowIterator;
    private final Workbook workbook;

    public PersonReader(@Value("#{jobParameters['filePath']}") String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        this.workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        this.rowIterator = sheet.iterator();
        if (this.rowIterator.hasNext()) this.rowIterator.next();

    }

    public PersonReader(Iterator<Row> rowIterator, Workbook workbook) {
        this.rowIterator = rowIterator;
        this.workbook = workbook;
    }

    @Override
    public Person read() throws Exception {
        if (!rowIterator.hasNext()) return null;

        Row row = rowIterator.next();
        Person person = new Person();

        person.setUserId(row.getCell(0).getStringCellValue());
        person.setFirstName(row.getCell(1).getStringCellValue());
        person.setLastName(row.getCell(2).getStringCellValue());
        person.setGender(row.getCell(3).getStringCellValue());
        person.setEmail(row.getCell(4).getStringCellValue());
        person.setPhone(row.getCell(5).getStringCellValue());
        person.setDateOfBirth(row.getCell(6).getStringCellValue()); // adjust if format differs
        person.setJobTitle(row.getCell(7).getStringCellValue());

        return person;
    }

    @PreDestroy
    public void closeWorkbook() throws IOException {
        workbook.close();
    }

}