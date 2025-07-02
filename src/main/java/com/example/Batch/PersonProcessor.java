package com.example.Batch;

import com.example.Batch.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PersonProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonProcessor.class);

    @Override
    public Person process(final Person person) {

        // Transform fields
        if (person.getFirstName() == null){
            return person;
        }else{
        final String firstName = person.getFirstName() != null ? person.getFirstName().toUpperCase() : null;
        final String lastName = person.getLastName() != null ? person.getLastName().toUpperCase() : null;

        Person transformedPerson = new Person();
        transformedPerson.setId(person.getId());
        transformedPerson.setUserId(person.getUserId());
        transformedPerson.setFirstName(firstName);
        transformedPerson.setLastName(lastName);
        transformedPerson.setGender(person.getGender());
        transformedPerson.setEmail(person.getEmail());
        transformedPerson.setPhone(person.getPhone());
        transformedPerson.setDateOfBirth(person.getDateOfBirth());
        transformedPerson.setJobTitle(person.getJobTitle());

        log.info("Converting ({}) into ({})", person, transformedPerson);

        return transformedPerson;
        }
    }
}
