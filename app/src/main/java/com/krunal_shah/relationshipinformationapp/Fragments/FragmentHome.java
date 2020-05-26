package com.krunal_shah.relationshipinformationapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krunal_shah.relationshipinformationapp.R;

import java.util.ArrayList;

public class FragmentHome extends android.app.Fragment {
    ListView listView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    ArrayList<String> list;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);

        firebaseAuth = firebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        list = new ArrayList<>();

        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.user_info, R.id.userInfo, list);
        listView.setAdapter(adapter);

        myRef.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    list.add(sn.getKey() + " : " + sn.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return view;
    }
}