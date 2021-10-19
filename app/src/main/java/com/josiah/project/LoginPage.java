package com.josiah.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    String strPassword;
    String strUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Database database = new Database(LoginPage.this);
        if(!database.checkUsername("admin")){
            boolean success = database.createAccount(new Admin("FIRST", "LAST", "admin", "admin123"));
            if (!success) {
                Toast.makeText(getApplicationContext(), "Error: Initialization Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loginAccount(View view) {
        strUsername = ((EditText) findViewById(R.id.textEnterUsername)).getText().toString();
        strPassword = ((EditText) findViewById(R.id.textEnterPassword)).getText().toString();

        Database database = new Database(LoginPage.this);

        if(strUsername.equals("") || strUsername.contains(" ") || strPassword.equals("") || strPassword.contains(" ")){
            Toast.makeText(getApplicationContext(),"Error: Username and/or password contains invalid characters.",Toast. LENGTH_SHORT).show();
        }
        else{
            if(database.checkUsername(strUsername)){
                if(database.checkPassword(strUsername, strPassword)){
                    Toast.makeText(getApplicationContext(),strUsername + " " + strPassword + " Success: True - Login Successful",Toast. LENGTH_SHORT).show();
                    // Login Successful, do stuff.
                    Account account = database.getAccount(strUsername);
                    String classPage = getPackageName() + "." + database.getRole(strUsername) + "Page";
                    try{
                        Class<?> c = Class.forName(classPage);
                        Intent intent = new Intent(getApplicationContext(), c).putExtra("Account", account);
                        startActivity(intent);
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(),"Fatal Error: Signing into user page failed. " + classPage ,Toast. LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),strUsername + " " + strPassword + " Success: False - Password Incorrect",Toast. LENGTH_SHORT).show();
                    // Login Failed, Password Not Correct.
                }
            }
            else{
                Toast.makeText(getApplicationContext(),strUsername + " " + strPassword + " Success: False - User Does Not Exist",Toast. LENGTH_SHORT).show();
                // Login Failed, User does not exist.
            }
        }
    }

    public void pageCreateAccount(View view) {

        Intent intent = new Intent(getApplicationContext(), CreatePage.class);
        startActivity(intent);
    }
}