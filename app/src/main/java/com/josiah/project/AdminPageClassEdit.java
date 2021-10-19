package com.josiah.project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPageClassEdit extends AppCompatActivity {

    int id;
    String nameEdit;
    String descriptionEdit;
    String nameEditPrev;
    Class_ c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_classes_edit);

        c = (Class_) this.getIntent().getSerializableExtra("Class");
        DatabaseClass db = new DatabaseClass(AdminPageClassEdit.this);

        ((TextView)findViewById(R.id.textEditClassID)).setText(String.valueOf(db.getID(c.getName())));
        ((EditText)findViewById(R.id.textEnterClassNameEdit)).setText(c.getName());
        ((EditText)findViewById(R.id.textEnterClassDescriptionEdit)).setText(c.getDescription());


        nameEdit = ((EditText) findViewById(R.id.textEnterClassNameEdit)).getText().toString();
        nameEditPrev = nameEdit;

        db.close();

    }

    public void cancelEdit(View view) {
        finish();
    }

    public void saveEdit(View view) {

        DatabaseClass db = new DatabaseClass(AdminPageClassEdit.this);
        id = Integer.parseInt(((TextView) findViewById(R.id.textEditClassID)).getText().toString());
        nameEdit = ((EditText) findViewById(R.id.textEnterClassNameEdit)).getText().toString();
        descriptionEdit = ((EditText) findViewById(R.id.textEnterClassDescriptionEdit)).getText().toString();

        if(nameEdit.equals("") || descriptionEdit.equals("")){
            Toast.makeText(getApplicationContext(),"Error: Please do not leave any of the fields empty.",Toast.LENGTH_SHORT).show();
        }
        else if(nameEdit.contains(" ") || descriptionEdit.equals(" ")){
            Toast.makeText(getApplicationContext(),"Error: Please do not use spaces or blank characters.",Toast.LENGTH_SHORT).show();
        }
        else if(db.checkName(nameEdit) && !nameEdit.equals(nameEditPrev)){
            Toast.makeText(getApplicationContext(),"Error: This username already exists.",Toast.LENGTH_SHORT).show();
        }
        else {
            db.updateClass(id, nameEdit, descriptionEdit);
            finish();
        }

        db.close();
    }
}
