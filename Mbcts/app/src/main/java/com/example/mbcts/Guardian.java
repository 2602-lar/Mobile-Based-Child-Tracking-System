package com.example.mbcts;

public class Guardian {
    private String name;
    private String surname;
    private Integer number_of_children;
    private String address;
    private String phonenumber;
    private String phonenumber_2;
    private String relationship;
    private String guardian_id ;

    public Guardian(
            String name, String surname, Integer number_of_children,
            String address, String phonenumber, String phonenumber_2, String relationship,
            String guardian_id) {
        this.name = name;
        this.surname = surname;
        this.number_of_children = number_of_children;
        this.address = address;
        this.phonenumber = phonenumber;
        this.phonenumber_2 = phonenumber_2;
        this.relationship = relationship;
        this.guardian_id = guardian_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getNumber_of_children() {
        return number_of_children;
    }

    public void setNumber_of_children(Integer number_of_children) {
        this.number_of_children = number_of_children;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber_2() {
        return phonenumber_2;
    }

    public void setPhonenumber_2(String phonenumber_2) {
        this.phonenumber_2 = phonenumber_2;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getGuardian_id() {
        return guardian_id;
    }

    public void setGuardian_id(String guardian_id) {
        this.guardian_id = guardian_id;
    }
}



