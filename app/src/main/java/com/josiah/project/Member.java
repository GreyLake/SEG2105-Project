package com.josiah.project;

public class Member extends Account{
    Member(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        super.role = "Member";
    }
}
