package com.example.schoolexamapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @Column(unique = true)
    private long rollNo;
    private int schoolClass;
    private char schoolSection;
    private String name;

    // removing password from the json response for security purposes
    @JsonIgnore
    private String password;
}
