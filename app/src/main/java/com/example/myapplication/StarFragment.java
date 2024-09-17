package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StarFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;

    public StarFragment() {
        // Required empty public constructor
    }

    public static StarFragment newInstance() {
        return new StarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // SharedPreferences에서 데이터를 읽어와서 favoriteAdapter에 추가
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<FavoriteItem> favoriteItems = new ArrayList<>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("title_")) {
                String key = entry.getKey().substring("title_".length());
                String title = entry.getValue().toString();
                String snippet = sharedPreferences.getString("snippet_" + key, "");
                favoriteItems.add(new FavoriteItem(title, snippet,key));
            }
        }

        favoriteAdapter = new FavoriteAdapter(favoriteItems);
        recyclerView.setAdapter(favoriteAdapter);

        return view;
    }
}