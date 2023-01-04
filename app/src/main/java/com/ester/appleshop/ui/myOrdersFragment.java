package com.ester.appleshop.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ester.appleshop.Models.MyCartModel;
import com.ester.appleshop.Models.MyOrderModel;
import com.ester.appleshop.R;
import com.ester.appleshop.adapter.MyCartAdapter;
import com.ester.appleshop.adapter.MyOrderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myOrdersFragment extends Fragment {

    RecyclerView recyclerView;

    MyOrderAdapter orderAdapter;
    List<MyOrderModel> orderModelList;
    ProgressBar progressBar;
    View empty,noEmpty;

    FirebaseFirestore db;
    FirebaseAuth auth;


    public myOrdersFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        // data base connection
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //Progress bar
        progressBar = root.findViewById(R.id.progressbar2);
        progressBar.setVisibility(View.VISIBLE);

        empty = root.findViewById(R.id.constraint1_order);
        empty.setVisibility(View.VISIBLE);

        noEmpty = root.findViewById(R.id.constraint2_order);

        recyclerView = root.findViewById(R.id.recyclerview_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);

        orderModelList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getActivity(), orderModelList);
        recyclerView.setAdapter(orderAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentId = documentSnapshot.getId();

                                MyOrderModel orderModel = documentSnapshot.toObject(MyOrderModel.class);


                                orderModelList.add(orderModel);
                                orderAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                empty.setVisibility(View.GONE);
                                noEmpty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        return root;
    }

}