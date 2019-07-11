package com.example.whjth.swipelayout_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Person> PersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPerson();
        PersonAdapter adapter = new PersonAdapter(MainActivity.this, R.layout.activity_item, PersonList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void initPerson(){
        for(int i=0; i<2; i++) {
            Person person = new Person("Alex", R.drawable.p1);
            PersonList.add(person);
            person = new Person("Brandon", R.drawable.p2);
            PersonList.add(person);
            person = new Person("Charles", R.drawable.p3);
            PersonList.add(person);
            person = new Person("Davie", R.drawable.p4);
            PersonList.add(person);
            person = new Person("Eric", R.drawable.p5);
            PersonList.add(person);
            person = new Person("Fanny", R.drawable.p6);
            PersonList.add(person);
            person = new Person("Grace", R.drawable.p7);
            PersonList.add(person);
            person = new Person("Helen", R.drawable.p8);
            PersonList.add(person);
            person = new Person("Isabelle", R.drawable.p9);
            PersonList.add(person);
            person = new Person("Jenny", R.drawable.p10);
            PersonList.add(person);
        }
    }
}
