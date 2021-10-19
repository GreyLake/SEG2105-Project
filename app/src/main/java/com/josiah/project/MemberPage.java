package com.josiah.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemberPage extends AppCompatActivity {

    Account_ account;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_member);

            account = (Account_) this.getIntent().getSerializableExtra("Account");

        TextView title = findViewById(R.id.strTitleMember);
        String text1, text2, text3;
        text1 = "Welcome " + account.getFirstName() + "! You are logged in as " + account.getRole();
        text2 = "\n\nHere are some basic things about you:\n";
        text3 = "\nUsername: " + account.getUsername() + "\nFirst Name: " + account.getFirstName() + "\nLast Name: " + account.getLastName() + "\nRole: " + account.getRole();
        title.setText(text1 + text2 + text3);
    }

    public void pageLogoutMember(View view) {
        finish();
    }
}
