package com.ester.appleshop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ester.appleshop.R;


public class creditFragment extends Fragment {

    TextView tvContact;
    public creditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.credit_fragment, container, false);

        tvContact = root.findViewById(R.id.tvContect);
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("email to:"));
                email.setType("message/rfc822");
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{"ester.412020025@civitas.ukrida.ac.id"});
                startActivity(Intent.createChooser(email,"send Mail"));
            }
        });

        return root;
    }
}