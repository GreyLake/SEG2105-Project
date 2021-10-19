package com.josiah.project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPageClassCreate extends AppCompatActivity {

    String name;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_classes_create);
    }

    public void createClass(View view){

        name = ((EditText) findViewById(R.id.textEnterClassName)).getText().toString();
        description = ((EditText) findViewById(R.id.textEnterClassDescription)).getText().toString();

        DatabaseClass db = new DatabaseClass(AdminPageClassCreate.this);

        if(name.contains(" ") || description.equals(" ")) {
            Toast.makeText(getApplicationContext(),"Error: Please do not use spaces or blank characters.",Toast.LENGTH_SHORT).show();
        }
        else if(db.checkName(name)){
            Toast.makeText(getApplicationContext(),"Error: This class Name already exists.",Toast.LENGTH_SHORT).show();
        }
        else {

            Class_ c;
            boolean success = false;

                try {
                    c = new Class_(name, description);
                    success = db.createClass(c);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: Creating Member Failed", Toast.LENGTH_SHORT).show();
                }

            if(success){
                finish();
            }
        }
        db.close();
    }

    public void back(View view) {
        finish();
    }
}
