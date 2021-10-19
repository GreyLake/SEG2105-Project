package com.josiah.project;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPageStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_statistics);
    }

    public void goAdminPageStatistics(View view) {
    }

    public void back(View view){
        finish();
    }
}
