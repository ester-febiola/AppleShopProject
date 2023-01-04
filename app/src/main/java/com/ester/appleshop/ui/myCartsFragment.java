package com.ester.appleshop.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ester.appleshop.Models.MyCartModel;
import com.ester.appleshop.R;
import com.ester.appleshop.activities.PlacedOrderActivity;
import com.ester.appleshop.adapter.MyCartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class myCartsFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmmount;

    RecyclerView recyclerView;

    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    ProgressBar progressBar;

    View empty,noEmpty;

    Button buyNow;




    public myCartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);
        // data base connection
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //Progress bar
        progressBar = root.findViewById(R.id.progressbar2);
        progressBar.setVisibility(View.VISIBLE);

        empty = root.findViewById(R.id.contraint1);
        empty.setVisibility(View.VISIBLE);
        noEmpty = root.findViewById(R.id.constraint2);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);

        overTotalAmmount = root.findViewById(R.id.textView6);

        buyNow = root.findViewById(R.id.buynow);

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentId = documentSnapshot.getId();

                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                                cartModel.setDocumentId((documentId));
                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                empty.setVisibility(View.GONE);
                                noEmpty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

                buyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                        intent.putExtra("itemList",(Serializable) cartModelList);
                        startActivity(intent);
                    }
                });

        return root;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount",0);
            overTotalAmmount.setText("Total Bill:" + totalBill+"$");

        }
    };

}