package com.ester.appleshop.ui.profile;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.ester.appleshop.R;
import com.ester.appleshop.activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    CircleImageView profileImg;

    EditText editName, editEmail, editNumber, editAddress;
    Button update, logout;

    FirebaseStorage storage;
    FirebaseAuth auth;
    DatabaseReference database;

    String uid;

    String name, email, number, address, password;

    private void getData() {
        uid = auth.getCurrentUser().getUid();
        database.child("User").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                //name
                String nameUser = String.valueOf(dataSnapshot.child("name").getValue());
                editName.setText(nameUser);
                //email
                String emailUser = String.valueOf(dataSnapshot.child("email").getValue());
                editEmail.setText(emailUser);
                //number
                String numberUser = String.valueOf(dataSnapshot.child("number").getValue());
                editNumber.setText(numberUser);
                //pass
                String addressUser = String.valueOf(dataSnapshot.child("address").getValue());
                editAddress.setText(addressUser);

                //password
                password = String.valueOf(dataSnapshot.child("password").getValue());


            }
        });
    }

    private void editData() {

        name = editName.getText().toString();
        email = editEmail.getText().toString();
        number = editNumber.getText().toString();
        address = editAddress.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("number", number);
        map.put("address", address);
        map.put("password", password);
        database.child("User").child(uid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        editName = root.findViewById(R.id.profile_name);
        editEmail = root.findViewById(R.id.profile_email);
        editNumber = root.findViewById(R.id.profile_phone);
        editAddress = root.findViewById(R.id.profile_address);
        update = root.findViewById(R.id.update);
        logout = root.findViewById(R.id.logout);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        getData();


        return root;
    }

}

