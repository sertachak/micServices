package com.sha.rabbitdemo.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CsvController {

    private final PersonService personService;

    public CsvController(PersonService entityService) {
        this.personService = entityService;
    }


    @PostMapping
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file){
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows;
            try (CSVReader csvReader = new CSVReader(reader)) {
                rows = csvReader.readAll();
            } catch (CsvException e) {
                return ResponseEntity.ok("Not OK");
            }

            // Convert CSV rows to entities
            List<Person> entities = personService.convertCsvToEntities(rows);

            // Persist entities (uncomment if applicable)
            // entityService.saveEntities(entities);

            return ResponseEntity.ok("OK");
        } catch (IOException e) {
            return ResponseEntity.ok("Not OK");
        }
    }
}
