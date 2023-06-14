package com.mediscreen.clientui.beans;

import java.time.LocalDate;

public class AssessmentBean {
    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    private String assessment;

    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    @Override
    public String toString() {
        return "AssessmentBean{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", assessment='" + assessment + '\'' +
                '}';
    }
}
