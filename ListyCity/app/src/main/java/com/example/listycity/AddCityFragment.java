package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city_to_edit"; // Key for the city object in Bundle
    private static final String ARG_POSITION = "city_position"; // Key for the city position in Bundle

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position); // New method for editing
    }

    private AddCityDialogListener listener;
    private City existingCity; // To store the city being edited
    private int existingCityPosition = -1; // To store the position of the city being edited

    // Required empty public constructor
    public AddCityFragment() {}

    // New static newInstance method for editing
    public static AddCityFragment newInstance(City city, int position) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    // New static newInstance method for adding (maintains original functionality)
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        String dialogTitle = "Add City";
        String positiveButtonText = "Add";

        if (getArguments() != null) {
            existingCity = (City) getArguments().getSerializable(ARG_CITY);
            existingCityPosition = getArguments().getInt(ARG_POSITION, -1);
            if (existingCity != null) {
                editCityName.setText(existingCity.getName());
                editProvinceName.setText(existingCity.getProvince());
                dialogTitle = "Edit City"; // Change title for editing
                positiveButtonText = "Update"; // Change button text for editing
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();

                    if (cityName.isEmpty() || provinceName.isEmpty()) {
                        // You might want to show an error message to the user here
                        return;
                    }

                    if (existingCity != null && existingCityPosition != -1) {
                        // Editing existing city
                        existingCity.setName(cityName);
                        existingCity.setProvince(provinceName);
                        listener.editCity(existingCity, existingCityPosition);
                    } else {
                        // Adding new city
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
