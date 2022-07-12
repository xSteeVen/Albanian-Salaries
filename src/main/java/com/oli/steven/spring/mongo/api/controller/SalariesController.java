package com.oli.steven.spring.mongo.api.controller;

import com.oli.steven.spring.mongo.api.model.Salaries;
import com.oli.steven.spring.mongo.api.repository.SalariesRepository;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequestMapping("/api")
@RestController
public class SalariesController {

        private static final String FILE_NAME = "src/main/resources/salaries1.xlsx";
        private static final DataFormatter formatter = new DataFormatter(Locale.US);
        private final MongoTemplate mongoTemplate;

        @Autowired
        private SalariesRepository salariesRepository;

        public SalariesController(MongoTemplate mongoTemplate) {
                this.mongoTemplate = mongoTemplate;
        }

        @PostMapping("/addCitizen")
        public void addCitizen(@RequestBody Salaries newCitizen) {
                salariesRepository.save(newCitizen);
        }

        @GetMapping("/import")
        public void importExcelFile() throws IOException {

                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
                List<Salaries> salariesList = new ArrayList<Salaries>();
                XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
                XSSFSheet worksheet = workbook.getSheetAt(0);

                for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                        Salaries salariePerson = new Salaries();

                        XSSFRow row = worksheet.getRow(i);
                        if (row == null) {
                                continue;
                        } else {

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
                }
                        salariesRepository.saveAll(salariesList);
                        System.out.println("Added: " + salariesList.size());
        }

        @GetMapping("/readExcel")
        public List<Salaries> readExcelFile() throws IOException {

                FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
                List<Salaries> salariesList = new ArrayList<Salaries>();
                XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
                XSSFSheet worksheet = workbook.getSheetAt(0);

                for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                        Salaries salariePerson = new Salaries();

                        XSSFRow row = worksheet.getRow(i);
                        if (row == null) {
                                continue;
                        } else {

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
                }
                return salariesList;
        }

        @GetMapping("/highSalary")
        public List<Salaries> highSalarie(@RequestParam(required = true) double salarieAmount){
                Query query = new Query();
                query.addCriteria(Criteria.where("salary").gt(salarieAmount));
                List<Salaries> salarieGT = mongoTemplate.find(query, Salaries.class);
                return salarieGT;
        }

        @GetMapping("/lowSalary")
        public List<Salaries> lowSalarie(@RequestParam(required = true) double salarieAmount){
                Query query = new Query();
                query.addCriteria(Criteria.where("salary").lt(salarieAmount));
                List<Salaries> salarieLT = mongoTemplate.find(query, Salaries.class);
                return salarieLT;
        }

        @GetMapping("/findByFirstName")
        public List<Salaries> firstName(@RequestParam(required = true) String firstName){
                Query query = new Query();
                query.addCriteria(Criteria.where("firstName").is(firstName));
                List<Salaries> nameEqual = mongoTemplate.find(query, Salaries.class);
                return nameEqual;
        }

        @GetMapping("/findByLastName")
        public List<Salaries> lastName(@RequestParam(required = true) String LastName){
                Query query = new Query();
                query.addCriteria(Criteria.where("lastName").is(LastName));
                List<Salaries> nameEqual = mongoTemplate.find(query, Salaries.class);
                return nameEqual;
        }

        @GetMapping("/peopleWithHighestSalary")
        public List<Salaries> highestSalary(@RequestParam(required = true) int Amount){
                Query query = new Query();
                query.with(Sort.by(Sort.Direction.DESC, "salary"));
                query.limit(Amount);
                List<Salaries> highestSalaries = mongoTemplate.find(query, Salaries.class);
                return highestSalaries;
        }
}
