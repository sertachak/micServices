package com.sha.rabbitdemo.csv;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

        public List<Person> convertCsvToEntities(List<String[]> rows) {
            List<Person> entities = new ArrayList<>();
            rows.remove(0);
            for (String[] row : rows) {
                entities.add(new Person(row[0], row[1]));
            }
            return entities;
        }
}
