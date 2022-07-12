package com.oli.steven.spring.mongo.api.utilities;

import com.oli.steven.spring.mongo.api.model.Salaries;
import com.oli.steven.spring.mongo.api.repository.SalariesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class ExcelReaderUtility {

    private static final String FILE_NAME = "src/main/resources/salaries1.xlsx";
    private static final DataFormatter formatter = new DataFormatter(Locale.US);
    private final SalariesRepository salariesRepository;
    private final MongoTemplate mongoTemplate;

    public List<Salaries> getAllSalaries() throws IOException {
        List<Salaries> salariesList = new ArrayList<Salaries>();
        XSSFSheet worksheet = readExcelFile();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Salaries salariePerson = new Salaries();
            XSSFRow row = worksheet.getRow(i);
            if (Objects.nonNull(row)) {
                generateSalaryRecord(salariesList, salariePerson, row);
            }
        }
        salariesRepository.saveAll(salariesList);
        log.info("Imported records to mongo db. The number of records: " + salariesList.size());
        return salariesList;
    }


    private void generateSalaryRecord(List<Salaries> salariesList, Salaries salariePerson, XSSFRow row) {
        salariePerson.setFirstName(formatter.formatCellValue(row.getCell(0)));
        salariePerson.setLastName(formatter.formatCellValue(row.getCell(1)));
        salariePerson.setFullName(formatter.formatCellValue(row.getCell(2)));
        salariePerson.setTaxId(formatter.formatCellValue(row.getCell(3)));
        salariePerson.setCompanyName(formatter.formatCellValue(row.getCell(4)));
        salariePerson.setPlaceOfWork(formatter.formatCellValue(row.getCell(5)));
        salariePerson.setSalary((double) row.getCell(6).getNumericCellValue());
        salariePerson.setOccupation(formatter.formatCellValue(row.getCell(7)));
        salariePerson.setNotes(formatter.formatCellValue(row.getCell(8)));
        salariesList.add(salariePerson);
    }

    private XSSFSheet readExcelFile() throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        return workbook.getSheetAt(0);
    }

}
