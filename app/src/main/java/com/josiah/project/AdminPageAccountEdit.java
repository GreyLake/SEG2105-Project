package com.josiah.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class AdminPageAccountEdit extends AppCompatActivity {

    String strAccountTypeEdit;
    String strFirstNameEdit;
    String strLastNameEdit;
    String strPasswordEdit;
    String strUsernameEdit;
    String strUsernamePrev;
    String strRoleEdit;
    Spinner spinnerAccountTypeEdit;
    Account_ account;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accounts_edit);

        account = (Account_) this.getIntent().getSerializableExtra("Account");
        DatabaseAccount db = new DatabaseAccount(AdminPageAccountEdit.this);

        int i;

        if(account.getRole().equals("Member")){
            i=0;
        }
        else
            i=1;

        spinnerAccountTypeEdit = findViewById(R.id.listAccountTypeEdit);
        String[] items = new String[]{"Member", "Instructor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerAccountTypeEdit.setAdapter(adapter);
        spinnerAccountTypeEdit.setSelection(i);
        ((TextView)findViewById(R.id.textAdminEditAccountsID)).setText(String.valueOf(db.getID(account.getUsername())));
        ((EditText)findViewById(R.id.textEnterFirstNameEdit)).setText(account.getFirstName());
        ((EditText)findViewById(R.id.textEnterLastNameEdit)).setText(account.getLastName());
        ((EditText)findViewById(R.id.textEnterUsernameEdit)).setText(account.getUsername());
        ((EditText)findViewById(R.id.textEnterPasswordEdit)).setText(account.getPassword());

        strUsernameEdit = ((EditText) findViewById(R.id.textEnterUsernameEdit)).getText().toString();
        strUsernamePrev = strUsernameEdit;

        db.close();

    }

    public void saveEdit(View view){

        DatabaseAccount db = new DatabaseAccount(AdminPageAccountEdit.this);

        id = Integer.parseInt(((TextView) findViewById(R.id.textAdminEditAccountsID)).getText().toString());
        strUsernameEdit = ((EditText) findViewById(R.id.textEnterUsernameEdit)).getText().toString();
        strPasswordEdit = ((EditText) findViewById(R.id.textEnterPasswordEdit)).getText().toString();
        strFirstNameEdit = ((EditText) findViewById(R.id.textEnterFirstNameEdit)).getText().toString();
        strLastNameEdit = ((EditText) findViewById(R.id.textEnterLastNameEdit)).getText().toString();
        strAccountTypeEdit = String.valueOf(spinnerAccountTypeEdit.getSelectedItem());
        strRoleEdit = String.valueOf(spinnerAccountTypeEdit.getSelectedItem());

        if(strPasswordEdit.equals("") || strUsernameEdit.equals("") || strFirstNameEdit.equals("") || strLastNameEdit.equals("")){
            Toast.makeText(getApplicationContext(),"Error: Please do not leave any of the fields empty.",Toast.LENGTH_SHORT).show();
        }
        else if(strPasswordEdit.contains(" ") || strUsernameEdit.contains(" ") || strFirstNameEdit.contains(" ") || strLastNameEdit.contains(" ")){
            Toast.makeText(getApplicationContext(),"Error: Please do not use spaces or blank characters.",Toast.LENGTH_SHORT).show();
        }
        else if(db.checkUsername(strUsernameEdit) && !strUsernameEdit.equals(strUsernamePrev)){
            Toast.makeText(getApplicationContext(),"Error: This username already exists.",Toast.LENGTH_SHORT).show();
        }
        else {
            db.updateAccount(id, strFirstNameEdit, strLastNameEdit, strUsernameEdit, strRoleEdit, strPasswordEdit);
            finish();
        }

        db.close();
    }

    public void cancelEdit(View view){
        finish();
    }
}
