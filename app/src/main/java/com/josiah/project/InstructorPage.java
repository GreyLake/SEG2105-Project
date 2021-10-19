package com.josiah.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class InstructorPage extends AppCompatActivity {

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_instructor);

            account = (Account) this.getIntent().getSerializableExtra("Account");
    }
}
