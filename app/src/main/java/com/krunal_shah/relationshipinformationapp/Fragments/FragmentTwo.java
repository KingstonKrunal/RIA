package com.krunal_shah.relationshipinformationapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.krunal_shah.relationshipinformationapp.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentTwo extends android.app.Fragment {
    Context context;
    Button btnSave;
    EditText inputPinCode, inputAltMobileNo, inputAge, inputAddress, inputDate;
    String pincode, altmobile, age, address, date;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().getRoot();
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        context = getActivity();

        Bundle bundle = this.getArguments();
        final String email = bundle.getString("Email");
        final String custID = bundle.getString("Username");
        final String fullname = bundle.getString("Fullname");
        final String mobileNo = bundle.getString("MobileNo");

        btnSave = view.findViewById(R.id.btnSave);
        inputAge = view.findViewById(R.id.register_Age);
        inputPinCode = view.findViewById(R.id.register_pincode);
        inputAltMobileNo = view.findViewById(R.id.register_altMobileNo);
        inputAddress = view.findViewById(R.id.register_address);
        inputDate = view.findViewById(R.id.register_date);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        mRef.keepSynced(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincode = inputPinCode.getText().toString().trim();
                altmobile = inputAltMobileNo.getText().toString().trim();
                age = inputAge.getText().toString().trim();
                address = inputAddress.getText().toString().trim();
                date = inputDate.getText().toString().trim();

                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("Email", email);
                dataMap.put("Customer ID", custID);
                dataMap.put("Full Name", fullname);
                dataMap.put("Mobile No", mobileNo);
                dataMap.put("Pin Code", pincode);
                dataMap.put("Age", age);
                dataMap.put("Alternate Mobile No", altmobile);
                dataMap.put("Address", address);
                dataMap.put("Date", date);

                mRef.child("users").child(currentUser.getUid()).setValue(dataMap);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", email);
                editor.putString("custID", custID);
                editor.apply();

                FragmentHome f3 = new FragmentHome();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f3).commit();
            }
        });

        return view;
    }
}