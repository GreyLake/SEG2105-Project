package com.josiah.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);

            account = (Account) this.getIntent().getSerializableExtra("Account");

        TextView title = (TextView) findViewById(R.id.strTitleAdmin);
        title.setText(account.getFirstName());
    }
}
