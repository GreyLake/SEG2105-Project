package com.josiah.project;

public class Instructor extends Account_ {

    Instructor(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        super.role = "Instructor";
    }
}
