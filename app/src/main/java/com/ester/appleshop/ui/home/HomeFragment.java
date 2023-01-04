package com.ester.appleshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ester.appleshop.Models.HomeCategory;
import com.ester.appleshop.Models.PopularModel;
import com.ester.appleshop.Models.RecommendedModel;
import com.ester.appleshop.R;
import com.ester.appleshop.adapter.HomeAdapter;
import com.ester.appleshop.adapter.PopularAdapter;
import com.ester.appleshop.adapter.RecommendedAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView popularRec,homeCatRec,recommendedRec;
    FirebaseFirestore db;

    //Popular item
    PopularAdapter popularAdapter;
    List<PopularModel> popularModelList;
    //Home Category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;
    //Recommended item
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container,false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recomended_rec);

        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //popular item
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapter);

        //database popular item

        db.collection("PopularProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel;
                                popularModel = new PopularModel(document.getString("name"),document.getString("description"),
                                        document.getString("rating"), document.getString("discount"),document.getString("type"),
                                        (document.getString("img_url")));
//                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"error"+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //home category item
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);
        //database Home Category

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory;
                                homeCategory = new HomeCategory( document.getString("name"),
                                        document.getString("type"),document.getString("img_url"));
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();


                            }
                        } else {
                            Toast.makeText(getActivity(),"error"+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //recommended
        //recommended item
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);
        //database recommended

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"error"+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        return root;
    }
}