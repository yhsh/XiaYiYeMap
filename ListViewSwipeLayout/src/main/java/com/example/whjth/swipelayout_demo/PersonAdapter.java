package com.example.whjth.swipelayout_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by whjth on 2017/6/16.
 */

public class PersonAdapter extends ArrayAdapter<Person>{

    private int resourceId;
    private Context context;

    public PersonAdapter(Context context, int textViewResourceId, List<Person> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent){
        final Person person = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.delete = view.findViewById(R.id.delete_button);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.image.setImageResource(person.getImageId());
        viewHolder.name.setText(person.getName());

        viewHolder.delete = view.findViewById(R.id.delete_button);
        viewHolder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "you clicked delete button", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "you clicked image", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    class ViewHolder{
        ImageView image;
        TextView name;
        View delete;
    }
}
