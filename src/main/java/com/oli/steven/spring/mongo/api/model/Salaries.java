package com.oli.steven.spring.mongo.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString

@Document(collection = "Citizens")
public class Salaries {
        @Field("firstName")
        private String firstName;
        @Field("lastName")
        private String lastName;
        private String fullName;
        private String taxId;
        private String companyName;
        private String placeOfWork;
        @Field("salary")
        private double salary;
        private String occupation;
        private String notes;
}
