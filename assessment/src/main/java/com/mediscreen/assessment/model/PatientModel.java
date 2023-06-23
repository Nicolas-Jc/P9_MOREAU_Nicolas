package com.mediscreen.assessment.model;


import java.time.LocalDate;

public class PatientModel {

    private Integer id;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    private String sex;

    private String address;

    private String phoneNumber;

    public PatientModel(Integer id, String lastName, String firstName,
                        LocalDate birthDate, String sex, String address, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

}
