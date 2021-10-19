package com.josiah.project;

public class Admin extends Account{
    Admin(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        super.role = "Admin";
    }
}
