package com.josiah.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MemberPage extends AppCompatActivity {

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_member);

            account = (Account) this.getIntent().getSerializableExtra("Account");
    }
}
