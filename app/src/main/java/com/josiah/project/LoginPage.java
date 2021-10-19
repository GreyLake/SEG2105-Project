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

        DatabaseAccount databaseAccount = new DatabaseAccount(LoginPage.this);
        if(!databaseAccount.checkUsername("admin")){
            boolean success = databaseAccount.createAccount(new Admin("FIRST", "LAST", "admin", "admin123"));
            if (!success) {
                Toast.makeText(getApplicationContext(), "Error: Initialization Failed", Toast.LENGTH_SHORT).show();
            }
        }

        databaseAccount.close();
    }

    public void loginAccount(View view) {
        strUsername = ((EditText) findViewById(R.id.textEnterUsername)).getText().toString();
        strPassword = ((EditText) findViewById(R.id.textEnterPassword)).getText().toString();

        DatabaseAccount databaseAccount = new DatabaseAccount(LoginPage.this);

        if(strUsername.equals("") || strUsername.contains(" ") || strPassword.equals("") || strPassword.contains(" ")){
            Toast.makeText(getApplicationContext(),"Error: Username and/or password contains invalid characters.",Toast. LENGTH_SHORT).show();
        }
        else{
            if(databaseAccount.checkUsername(strUsername)){
                if(databaseAccount.checkPassword(strUsername, strPassword)){
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast. LENGTH_SHORT).show();
                    // Login Successful, do stuff.
                    Account_ account = databaseAccount.getAccount(strUsername);
                    String classPage = getPackageName() + "." + databaseAccount.getRole(strUsername) + "Page";
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
                    Toast.makeText(getApplicationContext(),"Password Incorrect",Toast. LENGTH_SHORT).show();
                    // Login Failed, Password Not Correct.
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"User Does Not Exist",Toast. LENGTH_SHORT).show();
                // Login Failed, User does not exist.
            }
        }
        databaseAccount.close();
    }

    public void pageCreateAccount(View view) {

        Intent intent = new Intent(getApplicationContext(), CreatePage.class);
        startActivity(intent);
    }

}