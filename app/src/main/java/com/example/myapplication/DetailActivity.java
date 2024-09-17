package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail); // 레이아웃 파일을 로드

        // 레이아웃의 뷰 요소를 참조
        ImageView detailImageView = findViewById(R.id.detailImageView);
        TextView detailTitleTextView = findViewById(R.id.detailTitleTextView);
        TextView detailDescriptionTextView = findViewById(R.id.detailDescriptionTextView);
        TextView detailAdditionalInfoTextView = findViewById(R.id.detailAdditionalInfoTextView);
        Button detailShareButton = findViewById(R.id.detailShareButton);
        Button detailDirectionsButton = findViewById(R.id.navigateButton);
        ImageButton infoButton = findViewById(R.id.infoButton);
        ImageButton imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String title = detailTitleTextView.getText().toString();
                String snippet = detailDescriptionTextView.getText().toString();

                // Check if the item is already in favorites
                if (!isItemInFavorites(sharedPreferences, title, snippet)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Generate a unique key based on the current time
                    long timestamp = System.currentTimeMillis();
                    String uniqueKey = String.valueOf(timestamp);

                    // FavoriteItem을 생성하고 저장
                    FavoriteItem favoriteItem = new FavoriteItem(title, snippet, uniqueKey);
                    editor.putString("title_" + uniqueKey, title);
                    editor.putString("snippet_" + uniqueKey, snippet);
                    editor.apply();

                    // Show the "Added to Favorites" custom toast
                    showCustomToast("즐겨찾기에 추가되었습니다");
                } else {
                    // Show the "Already in Favorites" custom toast
                    showCustomToast("이미 즐겨찾기에 있는 장소입니다.");
                }
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "info" 버튼을 클릭했을 때 InfoActivity로 이동
                startInfoActivity();
            }
        });

        // "공유하기" 버튼 클릭 시 동작
        detailShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(detailTitleTextView.getText().toString());
            }
        });

        // "길찾기" 버튼 클릭 시 동작
        detailDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDirections();
            }
        });

        // Intent로 전달받은 정보
        Intent intent = getIntent();
        if (intent != null) {
            LatLng latLng = intent.getParcelableExtra("LAT_LNG");
            String title = intent.getStringExtra("TITLE");
            String snippet = intent.getStringExtra("SNIPPET");
            String additionalInfo = intent.getStringExtra("ADDITIONALINFO");

            // 이미지 리소스 ID 가져오기 (기본값으로 설정)
            int imageResId = R.drawable.icon_qa; // 여기서 "default_image"는 기본 이미지 리소스입니다.

            // Intent로 이미지 리소스 ID가 전달되었는지 확인
            if (intent.hasExtra("IMAGE_RES_ID")) {
                imageResId = intent.getIntExtra("IMAGE_RES_ID", R.drawable.icon_qa);
            }

            // 정보를 뷰 요소에 설정
            detailTitleTextView.setText(title);
            detailDescriptionTextView.setText(snippet);
            detailAdditionalInfoTextView.setText(additionalInfo);
            detailImageView.setImageResource(imageResId);
        }

        // "공유하기" 버튼 클릭 시 동작
        detailShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(detailTitleTextView.getText().toString()); // 타이틀 텍스트를 가져와서 공유
            }
        });
    }

    private boolean isItemInFavorites(SharedPreferences sharedPreferences, String title, String snippet) {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("title_")) {
                String key = entry.getKey().substring("title_".length());
                String storedTitle = entry.getValue().toString();
                String storedSnippet = sharedPreferences.getString("snippet_" + key, "");

                if (title.equals(storedTitle) && snippet.equals(storedSnippet)) {
                    return true; // The item is already in favorites
                }
            }
        }
        return false; // The item is not in favorites
    }

    private void startDirections() {
        String destinationTitle = getIntent().getStringExtra("TITLE");

        // 목적지 title을 사용하여 Google 지도에서 길찾기를 시작합니다.
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationTitle + "&mode=transit");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Google Maps 앱이 설치되지 않았을 경우 대체 작업을 수행할 수 있음
            showCustomToast("Google Maps 앱이 설치되지 않았습니다.");
        }
    }

    private void shareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);

        startActivity(Intent.createChooser(shareIntent, "공유하기"));
    }

    private boolean isPackageInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    // 커스텀 토스트 메시지를 표시하는 메서드
    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    private void startInfoActivity() {
        // InfoActivity로 이동하는 코드
        Intent intent = new Intent(this, InfoActivity.class);

        // 전달해야 할 정보들을 Intent에 추가
        intent.putExtra("SOME_KEY", "Some value"); // 예시로 어떤 정보를 전달하려면 추가

        // InfoActivity를 시작
        startActivity(intent);
    }
}

