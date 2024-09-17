package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteItem> favorites;

    public FavoriteAdapter(List<FavoriteItem> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteItem favoriteItem = favorites.get(position);
        holder.titleTextView.setText(favoriteItem.getTitle());
        holder.snippetTextView.setText(favoriteItem.getSnippet());
    }

    // 아래 코드를 추가하여 아이템 목록 업데이트
    public void updateFavoritesList(List<FavoriteItem> updatedList) {
        this.favorites = updatedList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView snippetTextView;
        public ImageButton deleteButton;
        public ImageButton naviButton; // 추가된 부분

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            snippetTextView = view.findViewById(R.id.snippetTextView);
            deleteButton = view.findViewById(R.id.deleteButton);
            naviButton = view.findViewById(R.id.naviButton); // 추가된 부분

            // 삭제 버튼 클릭 이벤트 처리
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // 해당 아이템의 고유한 키 가져오기
                        String uniqueKey = favorites.get(position).getUniqueKey();

                        // SharedPreferences에서 해당 항목 삭제
                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("title_" + uniqueKey);
                        editor.remove("snippet_" + uniqueKey);
                        editor.apply();

                        // 아이템 삭제
                        favorites.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

            // 네비게이션 버튼 클릭 이벤트 처리
            naviButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 여기에 길찾기 기능을 추가하십시오.
                    openGoogleMaps();
                }
            });
        }

        private void openGoogleMaps() {
            // 여기에 길찾기 기능을 추가하십시오.
            String destinationTitle = titleTextView.getText().toString();

            // 목적지 title을 사용하여 Google 지도에서 길찾기를 시작합니다.
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationTitle + "&mode=transit");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
                itemView.getContext().startActivity(mapIntent);
            } else {
                // Google Maps 앱이 설치되지 않았을 경우 대체 작업을 수행할 수 있음
                Toast.makeText(itemView.getContext(), "Google Maps app is not installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}