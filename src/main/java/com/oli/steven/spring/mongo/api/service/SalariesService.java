package com.oli.steven.spring.mongo.api.service;

import com.oli.steven.spring.mongo.api.exceptions.SalariesReadingException;
import com.oli.steven.spring.mongo.api.model.Salaries;
import com.oli.steven.spring.mongo.api.utilities.ExcelReaderUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class SalariesService {
    private final ExcelReaderUtility excelReaderUtility;

    public List<Salaries> getAllSalaries() {
        try{
            log.info("Reading all salaries...");
            return excelReaderUtility.getAllSalaries();
        } catch (IOException ioException) {
            log.error("An error happened! Error message: " + ioException.getLocalizedMessage());
            throw new SalariesReadingException();
        } catch (Exception e) {
            log.error("Unknown exception occured! Error messahe: "  + e.getLocalizedMessage());
            throw new SalariesReadingException();
        }
    }
}
