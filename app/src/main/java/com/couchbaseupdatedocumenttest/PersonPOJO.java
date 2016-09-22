package com.couchbaseupdatedocumenttest;

public class PersonPOJO {

    public String id;
    public String firstName;
    public String lastName;
    public String street;
    public String state;
    public String country;

    public PersonPOJO() {
    }

    public PersonPOJO(PersonPOJO personPOJO) {
        this.id = personPOJO.id;
        this.firstName = personPOJO.firstName;
        this.lastName = personPOJO.lastName;
        this.street = personPOJO.street;
        this.state = personPOJO.state;
        this.country = personPOJO.country;
    }

    public PersonPOJO(String id, String firstName, String lastName, String street, String state, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.state = state;
        this.country = country;
    }

    @Override
    public String toString() {
        return "PersonPOJO{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
