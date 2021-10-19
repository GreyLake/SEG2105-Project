package com.josiah.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminPageClassList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_classes);

            ListView classesList = findViewById(R.id.listAdminClasses);
            DatabaseClass db = new DatabaseClass(AdminPageClassList.this);
            ArrayList<Class_> allClasses = db.getAllClasses();

            if(allClasses.isEmpty()){
                Toast.makeText(getApplicationContext(), "No Classes Exist", Toast.LENGTH_SHORT).show();
            }
            else{
                AdminClassListAdapter classAdapter = new AdminClassListAdapter(allClasses, AdminPageClassList.this);
                classesList.setAdapter(classAdapter);
            }
    }

    public void goAdminPageClassCreate(View view){
        Intent intent = new Intent(getApplicationContext(), AdminPageClassCreate.class);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        recreate();
        Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
    }

    void onDelete(){
        ListView classesList = findViewById(R.id.listAdminClasses);
            DatabaseClass db = new DatabaseClass(AdminPageClassList.this);
            ArrayList<Class_> allClasses = db.getAllClasses();

            if(allClasses.isEmpty()){
                classesList.setAdapter(null);
            }
            else{
                AdminClassListAdapter classAdapter = new AdminClassListAdapter(allClasses, AdminPageClassList.this);
                classesList.setAdapter(classAdapter);
            }
            db.close();
    }
}
