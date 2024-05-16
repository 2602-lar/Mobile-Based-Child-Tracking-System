package com.example.mbcts;

import java.util.Date;

public class Child {

    private String firstname;
    private String surname;
    private String date_of_birth;
    private Integer age;
    private Integer number_of_guardians;
    private String child_id;
    private String guardian_id;

    public Child(String firstname, String surname, String date_of_birth, Integer age, Integer number_of_guardians, String child_id, String guardian_id) {
        this.firstname = firstname;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.age = age;
        this.number_of_guardians = number_of_guardians;
        this.child_id = child_id;
        this.guardian_id = guardian_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNumber_of_guardians() {
        return number_of_guardians;
    }

    public void setNumber_of_guardians(Integer number_of_guardians) {
        this.number_of_guardians = number_of_guardians;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getGuardian_id() {
        return guardian_id;
    }

    public void setGuardian_id(String guardian_id) {
        this.guardian_id = guardian_id;
    }
}
