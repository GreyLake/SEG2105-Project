package com.josiah.project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminPageAccountList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_accounts);

            ListView accountsList = findViewById(R.id.listAdminAccounts);
            DatabaseAccount db = new DatabaseAccount(AdminPageAccountList.this);
            ArrayList<Account_> allAccounts = db.getAllAccounts();

            //             Toast.makeText(AdminPageAccountList.this, allAccounts.toString(), Toast.LENGTH_SHORT).show();


            if(allAccounts.isEmpty()){
                Toast.makeText(getApplicationContext(), "No Accounts Exist", Toast.LENGTH_SHORT).show();
            }
            else{
                AdminAccountListAdapter accountAdapter = new AdminAccountListAdapter(allAccounts, AdminPageAccountList.this);
                accountsList.setAdapter(accountAdapter);
            }
            db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void back(View view){
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        recreate();
        Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
    }

    void onDelete(){
        ListView accountsList = findViewById(R.id.listAdminAccounts);
            DatabaseAccount db = new DatabaseAccount(AdminPageAccountList.this);
            ArrayList<Account_> allAccounts = db.getAllAccounts();

            //             Toast.makeText(AdminPageAccountList.this, allAccounts.toString(), Toast.LENGTH_SHORT).show();


            if(allAccounts.isEmpty()){
                accountsList.setAdapter(null);
            }
            else{
                AdminAccountListAdapter accountAdapter = new AdminAccountListAdapter(allAccounts, AdminPageAccountList.this);
                accountsList.setAdapter(accountAdapter);
            }
            db.close();
    }
}
