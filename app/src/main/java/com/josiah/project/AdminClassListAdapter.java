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

public class AdminClassListAdapter extends ArrayAdapter<Class_> {

    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        Button edit;
        Button delete;
    }

    public AdminClassListAdapter(ArrayList<Class_> data, Context context) {
        super(context, R.layout.classes_list, data);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Class_ c = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.classes_list, parent, false);
            viewHolder.name = convertView.findViewById(R.id.textNameClassList);
            viewHolder.edit = convertView.findViewById(R.id.btnEditClassList);
            viewHolder.delete = convertView.findViewById(R.id.btnDeleteClassList);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(c.getName());
        viewHolder.edit.setTag(position);
        viewHolder.delete.setTag(position);


        viewHolder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminPageClassEdit.class).putExtra("Class", c);
            context.startActivity(intent);
        });

        viewHolder.delete.setOnClickListener(v -> {
            DatabaseClass db = new DatabaseClass(context);

            db.deleteClass(db.getID(c.getName()));
            Toast.makeText(context, "Class Deleted", Toast.LENGTH_SHORT).show();

            db.close();

           ((AdminPageClassList)context).onDelete();
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
