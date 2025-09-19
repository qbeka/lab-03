package com.example.listycity;

import android.os.Bundle;
import android.widget.ListView; // Ensure ListView is imported

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {
    private ArrayList<City> dataList;
    private ListView cityList; // Ensure this is R.id.city_list from your XML
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged(); // Let adapter know data changed
    }

    // Implement the new editCity method
    @Override
    public void editCity(City city, int position) {
        if (position >= 0 && position < dataList.size()) {
            dataList.set(position, city); // Update the city at the given position
            cityAdapter.notifyDataSetChanged(); // Refresh the ListView
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure you are using the correct layout file that includes the Toolbar and FAB
        // For example, if you followed my previous advice for Toolbar and Black/White theme:
        setContentView(R.layout.activity_main); // Make sure this is your main layout with city_list and button_add_city

        // Initialize cityList if it's not the one from previous examples
        cityList = findViewById(R.id.city_list); // Use the ID from your activity_main.xml

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };
        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city); // Use the ID from your activity_main.xml
        fab.setOnClickListener(v -> {
            // Use the newInstance method for adding
            AddCityFragment.newInstance().show(getSupportFragmentManager(), "ADD_CITY_DIALOG");
        });

        // Add OnItemClickListener to ListView for editing
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City cityToEdit = dataList.get(position); // Or cityAdapter.getItem(position);
            // Use the newInstance method for editing, passing the city and its position
            AddCityFragment.newInstance(cityToEdit, position).show(getSupportFragmentManager(), "EDIT_CITY_DIALOG");
        });
    }
}
