package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    //위치권한 변수 선언
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    // 바텀 네비게이션
    BottomNavigationView bottomNavigationView;

    private String TAG = "메인";

    // 프래그먼트 변수

    Fragment fragment_star;
    Fragment fragment_qa;
    Fragment fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 생성

        fragment_map = new MapFragment();
        fragment_qa = new QAFragment();
        fragment_star = new StarFragment();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_map).commitAllowingStateLoss();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){

                    case R.id.menu_map:
                        Log.i(TAG, "MAP 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_map).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_qa:
                        Log.i(TAG, "QA 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_qa).commitAllowingStateLoss();
                        return true;
                    case R.id.menu_star:
                        Log.i(TAG, "STAR 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_star).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });

        // 위치 권한이 부여되어 있는지 확인합니다.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 부여되어 있으면 원하는 동작을 수행합니다.
            // 예: 위치 정보 수집 시작 등
        } else {
            // 권한이 부여되지 않았다면 권한 요청 대화상자를 표시합니다.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

    }
    // 권한 요청 결과를 처리하는 메서드를 추가합니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한이 부여되었을 때 원하는 동작을 수행합니다.
                // 예: 위치 정보 수집 시작 등
            } else {
                // 권한이 거부되었을 때 처리할 코드를 추가하세요.
                // 사용자에게 권한이 필요하다는 메시지를 표시하거나 다른 조치를 취할 수 있습니다.
            }
        }
    }
}