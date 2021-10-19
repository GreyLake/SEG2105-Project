package com.josiah.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {

    Account_ account;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);

            account = (Account_) this.getIntent().getSerializableExtra("Account");

    }

    public void pageLogoutAdmin(View view) {
        finish();
    }

    public void goAdminPageAccountList(View view){
        Intent intent = new Intent(getApplicationContext(), AdminPageAccountList.class);
        startActivity(intent);
    }

    public void goAdminPageClassList(View view){
        Intent intent = new Intent(getApplicationContext(), AdminPageClassList.class);
        startActivity(intent);
    }

    public void goAdminPageStatistics(View view){
        Intent intent = new Intent(getApplicationContext(), AdminPageStatistics.class);
        startActivity(intent);
    }
}


