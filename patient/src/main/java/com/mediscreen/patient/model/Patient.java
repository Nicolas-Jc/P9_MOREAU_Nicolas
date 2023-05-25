package com.mediscreen.patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Table(name = "patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueConstraint"
                        , columnNames = {"last_Name", "first_Name", "birthdate"})
        }
)
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @NotBlank(message = "Last Name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 50)
    @NotBlank(message = "First Name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth Date is mandatory")
    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Size(max = 1)
    @Pattern(regexp = "^[M|F]$", message = "M : Male / F : Female")
    @NotBlank(message = "Sex is mandatory")
    private String sex;

    @Size(max = 200)
    private String address;

    @Size(max = 30)
    @Column(name = "phone")
    // @Pattern(regexp = "^(33|0)(6|7|9)\\d{8}$"
    //, message = "Please Enter a valid phone number")
    private String phoneNumber;

    public Patient(long i, String lastname, String firstname, LocalDate of, String m, String address, String s) {
    }

    public Patient(String lastName, String firstName, LocalDate birthDate, String sex, String address, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient(Integer id, String lastName, String firstName, LocalDate birthDate, String sex, String address, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
