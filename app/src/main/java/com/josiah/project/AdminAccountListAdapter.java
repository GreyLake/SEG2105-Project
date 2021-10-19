package com.josiah.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminAccountListAdapter extends ArrayAdapter<Account_> {

    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView username;
        TextView firstName;
        TextView lastName;
        TextView role;
        Button edit;
        Button delete;
    }

    public AdminAccountListAdapter(ArrayList<Account_> data, Context context) {
        super(context, R.layout.accounts_list, data);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Account_ account = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.accounts_list, parent, false);
            viewHolder.firstName = convertView.findViewById(R.id.textFirstNameAccountList);
            viewHolder.lastName = convertView.findViewById(R.id.textLastNameAccountList);
            viewHolder.role = convertView.findViewById(R.id.textTypeAccountList);
            viewHolder.username = convertView.findViewById(R.id.textUsernameAccountList);
            viewHolder.edit = convertView.findViewById(R.id.btnEditAccountList);
            viewHolder.delete = convertView.findViewById(R.id.btnDeleteAccountList);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.firstName.setText(account.getFirstName());
        viewHolder.lastName.setText(account.getLastName());
        viewHolder.role.setText(account.getRole());
        viewHolder.username.setText(account.getUsername());
        viewHolder.edit.setTag(position);
        viewHolder.delete.setTag(position);


        viewHolder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminPageAccountEdit.class).putExtra("Account", account);
            context.startActivity(intent);
        });

        viewHolder.delete.setOnClickListener(v -> {
            DatabaseAccount db = new DatabaseAccount(context);

            db.deleteAccount(db.getID(account.getUsername()));
            Toast.makeText(context, "Account Deleted", Toast.LENGTH_SHORT).show();

            db.close();

           ((AdminPageAccountList)context).onDelete();
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
