package com.josiah.project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String strAccountType;
    String strFirstName;
    String strLastName;
    String strPassword;
    String strPasswordRepeat;
    String strUsername;
    Spinner spinnerAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        spinnerAccountType = findViewById(R.id.listAccountType);
        String[] items = new String[]{"Member", "Instructor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAccountType.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l){
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void createAccount(View view) {
        strUsername = ((EditText) findViewById(R.id.textEnterUsername)).getText().toString();
        strPassword = ((EditText) findViewById(R.id.textEnterPassword)).getText().toString();
        strPasswordRepeat = ((EditText) findViewById(R.id.textEnterPasswordRepeat)).getText().toString();
        strFirstName = ((EditText) findViewById(R.id.textEnterFirstName)).getText().toString();
        strLastName = ((EditText) findViewById(R.id.textEnterLastName)).getText().toString();
        strAccountType = String.valueOf(spinnerAccountType.getSelectedItem());

        DatabaseAccount databaseAccount = new DatabaseAccount(CreatePage.this);

        if(!strPassword.equals(strPasswordRepeat)){
            Toast.makeText(getApplicationContext(),"Error: Passwords do not match! Please try again.",Toast.LENGTH_SHORT).show();
        }
        else if(strPassword.equals("") || strUsername.equals("") || strFirstName.equals("") || strLastName.equals("")){
            Toast.makeText(getApplicationContext(),"Error: Please do not leave any of the fields empty.",Toast.LENGTH_SHORT).show();
        }
        else if(strPassword.contains(" ") || strUsername.contains(" ") || strFirstName.contains(" ") || strLastName.contains(" ")){
            Toast.makeText(getApplicationContext(),"Error: Please do not use spaces or blank characters.",Toast.LENGTH_SHORT).show();
        }
        else if(databaseAccount.checkUsername(strUsername)){
            Toast.makeText(getApplicationContext(),"Error: This username already exists.",Toast.LENGTH_SHORT).show();
        }
        else {

            Account_ account;
            boolean success = false;

            if(strAccountType.equals("Member")){
                try{
                    account = new Member(strFirstName, strLastName, strUsername, strPassword);
                    success = databaseAccount.createAccount(account);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error: Creating Member Failed", Toast.LENGTH_SHORT).show();
                }
            }
            else if(strAccountType.equals("Instructor")){
                try{
                    account = new Instructor(strFirstName, strLastName, strUsername, strPassword);
                    success = databaseAccount.createAccount(account);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error: Creating Member Failed", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Error: AccountType not found", Toast.LENGTH_SHORT).show();
            }

            if(success){
                Toast.makeText(getApplicationContext(), "Account created successfully" , Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        databaseAccount.close();
    }

    public void back(View view) {
        finish();
    }
}
