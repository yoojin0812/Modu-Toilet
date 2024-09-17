package com.example.myapplication;

import static androidx.appcompat.app.AlertDialog.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import android.Manifest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView = null;
    private GoogleMap mMap;
    private LatLng InputLatLng = null;
    private static String PlaceName;
    private static final String TAG = "MapFragment";
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLocation;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    AlertDialog alertDialog;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        // fusedLocationClient 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        return layout;
    }

    private void addMarkersOnMap() {
        if (mMap != null) {
            // 마커 정보를 담고 있는 배열 또는 리스트를 생성합니다.
            List<MarkerInfo> markerList = new ArrayList<>();

            // 마커 정보를 추가합니다. 예제에서는 더미 데이터를 사용했습니다.
            markerList.add(new MarkerInfo(new LatLng(37.5792, 126.967), "배화여대", "경복궁의 자랑", R.drawable.baewha, "baewha.ac.kr \n010-1004-1004"));
            markerList.add(new MarkerInfo(new LatLng(37.5780, 126.99), "종로4가 지하상가", "창경궁로 85", R.drawable.jonglo4ga, "전화번호 : 02-2290-6246 \n개방시간 : 정시"));
            markerList.add(new MarkerInfo(new LatLng(37.5704, 127.009), "동대문", "율곡로 308", R.drawable.dongdamun, "전화번호 : 02) 2290-6545 \n개방시간 : 정시(05:00-01:00) \n장애인 공용 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5699, 127.002), "마전교", "동호로 398", R.drawable.majunkyo, "전화번호 : 02) 2290-6249 \n개방시간 : 상시(05:00-01:00)"));
            markerList.add(new MarkerInfo(new LatLng(37.5706, 126.999), "종오", "종로 200", R.drawable.jongoh, "전화번호 : 02) 2290-6433 \n개방시간 : 정시(05:00-01:00) \n장애인 공용 화장실 있음 \n기저귀 교환대 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5704, 126.985), "종각", "종로 73", R.drawable.jonggak, "전화번호 : 02) 2290-6555 \n개방시간 : 정시(05:00-01:00) \n장애인 공용 화장실 있음 \n기저귀 교환대 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5746, 126.978), "광화문시민열린마당화장실", "종로1길 45", R.drawable.simin, "전화번호 : 02-3783-5950 \n개방시간 : 상시 \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5819, 127.006), "낙산공원정상화장실", "낙산1길 28-7", R.drawable.nacksanjungsang, "전화번호 : 02-743-7985 \n개방시간 : 상시 \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5819, 127.006), "낙산공원중앙광장화장실", "낙산1길 28-7", R.drawable.nacksanmiddle, "전화번호 : 02-743-7985 \n개방시간 : 상시 \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5706, 126.985), "종각역 지하상가", "종로 73", R.drawable.jonggak, "전화번호 : 02-2290-6521 \n개방시간 : 정시(05:00-23:00) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5807, 126.969), "통인시장 고객센터화장실", "자하문로15길 18", R.drawable.tongin, "전화번호 : 02-722-0911 \n개방시간 : 정시(08:00-22:00) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5779, 126.992), "창덕공원(1층)", "율곡로 99", R.drawable.changdeok, "전화번호 : 02-2148-2843 \n개방시간 : 정시(07:00~18:00) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5711, 126.988), "탑골공원(1층)", "종로 99", R.drawable.topgoal, "전화번호 : 02-2148-2843 \n개방시간 : 정시(09:00~18:00) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5729, 127.018), "동묘공원(1층)", "난계로27길 84", R.drawable.dongmyo, "전화번호 : 02-2148-2843 \n개방시간 : 정시(09:00~18:00) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5804, 127.002), "마로니에공원(1층,B1층)", "대학로 104", R.drawable.maro, "전화번호 : 02-2148-2842 \n개방시간 : 정시(07:00~24:00) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5860, 127.001), "주)중앙에너비스(혜화)", "창경궁로35길 1", R.drawable.middle, "전화번호 : 02-2148-2383 \n개방시간 : 상시(연중개방) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.6066, 126.971), "특종주유소(1층)", "평창문화로 90", R.drawable.oil, "전화번호 : 02-2148-2475 \n개방시간 : 상시(연중개방) \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5804, 127.002), "마로니에공원(1층, B1층)", "대학로 104", R.drawable.marronnier, "전화번호: 02-2148-2842 \n개방시간: 정시(07:00~24:00) \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5860, 127.001), "주)중앙에너비스(혜화)", "창경궁로35길 1", R.drawable.centerenervis, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5710, 127.002), "종로5가 지하상가", "종로 223-1", R.drawable.jongno_5, "전화번호: 02-2290-6433 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.6066, 126.971), "특종주유소(1층)", "평창문화로 90", R.drawable.scoop_gas_station, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5964, 126.964), "자하문주유소(1층)", "자하문로 248", R.drawable.jahamun_gas_station, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.599, 126.959), "대주페트로(주)종로지점안풍주유소(1층)", "자하문로 303", R.drawable.anpung_gas_station, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.6098, 126.974), "북악주유소(1층)", "평창문화로 137", R.drawable.bukak_gas_station, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5754, 126.980), "지에스칼텍스 경복궁점 (1층)", "율곡로 6", R.drawable.gs_gyeongbokgung, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5744, 126.966), "주식회사 대양씨앤씨(1층)", "사직로 65", R.drawable.daeyang, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5763, 126.985), "sk네트웍스(주)재동주유소(1층)", "율곡로 58", R.drawable.jaedong_gas_station, "전화번호: 02-2148-2475 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5763, 126.985), "무궁화동산(1층)", "자하문로26길 15", R.drawable.rosegarden, "전화번호: 02-2148-2383 \n개방시간: 상시 \n비상벨 있음 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5760, 127.017), "숭인공원(1층)", "지봉로12가길 21-11", R.drawable.sunginpark, "전화번호: 02-2148-2383 \n개방시간: 상시 \n비상벨 있음 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5707, 126.968), "경희궁공원(1층)", "새문안로 45", R.drawable.gyeonghuigungpark, "전화번호: 02-2148-2383 \n개방시간: 상시 \n비상벨 있음 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5892, 126.989), "와룡공원 입구(1층)", "", R.drawable.waryongpark, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5926, 126.981), "삼청공원 약수터(1층)", "지봉로 97-4", R.drawable.samcheongpark, "전화번호: 02-2148-2383 \n개방시간: 상시 \n비상벨 있음 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.6029, 126.982), "북한산도시자연공원 북악팔각정(1층)", "북악산로 267", R.drawable.bukak_octagonalpavilion, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5913, 126.971), "북악산도시자연공원 창의문(1층)", "평창동 산6-91", R.drawable.bukaksanurbannaturalpark, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5799, 126.963), "인왕산도시자연공원 인왕배드민턴장(1층)", "청운동 7-2", R.drawable.inwang_badminton, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5782, 126.960), "인왕산도시자연공원 인왕사(1층)", "통일로18가길 8", R.drawable.inwangsan_mountain, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5911, 126.966), "인왕산도시자연공원 청운지구(1층)", "창의문로3길 21", R.drawable.inwangsan_cheongun, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음 \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5911, 126.991), "와룡공원주변 공중화장실", "명륜10길 10-3", R.drawable.waryon_park, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.578629, 127.01736), "숭인저소득2지역 공중화장실", "동망산1길 18-9", R.drawable.soongin2, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.578642, 127.01758), "숭인저소득1지역 공중화장실", "동망산1길 22-12", R.drawable.soongin, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5755, 127.012), "당고개공원 공중화장실", "창신6나길 4", R.drawable.danggogae_park, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5750, 127.009), "창신저소득지역 공중화장실", "창신4가길 34-5", R.drawable.changsindong, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.6151, 127.974), "참샘골공원 공중화장실", "평창길 278-4", R.drawable.chamsamgol_park, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5773, 126.911), "인왕산등산로입구 공중화장실", "통일로18나길 17", R.drawable.inwangsan_urbannature_park, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5829, 127.963), "인왕산수목원약수터 공중화장실", "옥인길 82", R.drawable.inwangsan_arboretummineral_spring, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5834, 126.981), "춘추관앞 주차장 공중화장실", "팔판길 37", R.drawable.chunchugwan, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5784, 127.012), "창신동 공중화장실", "지봉로13길 48", R.drawable.changsindong, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.6063, 126.959), "구기동 공중화장실", "진흥로 468-1", R.drawable.gugidong, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음 \n기저귀 교환대 있음 \n비상벨 있음"));
            markerList.add(new MarkerInfo(new LatLng(37.5858, 126.981), "삼청동공중화장실", "삼청로 117", R.drawable.samcheondong, "전화번호: 02-2148-2383 \n개방시간: 상시 \n장애인 화장실 있음 \n기저귀 교환대 있음 \n비상벨 있음"));

            // 마커를 동적으로 생성하고 데이터를 설정합니다.
            for (MarkerInfo markerInfo : markerList) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(markerInfo.getLatLng())
                        .title(markerInfo.getTitle())
                        .snippet(markerInfo.getSnippet());
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.marker_);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                //float hue = 200.0f;
                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(hue));
                mMap.addMarker(markerOptions).setTag(markerInfo); // 마커에 MarkerInfo 객체를 설정하여 이미지 리소스 ID를 저장
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // ACCESS_FINE_LOCATION 권한을 체크하고 요청합니다.
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되었을 때 현재 위치를 가져와서 지도에 표시합니다.
            mMap.setMyLocationEnabled(true);

            // 현재 위치를 가져와 지도 중앙으로 이동시킵니다.
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // 현재 위치의 위도와 경도를 가져옵니다.
                                double currentLat = location.getLatitude();
                                double currentLng = location.getLongitude();

                                // LatLng 객체를 생성하여 현재 위치를 표현합니다.
                                LatLng currentLatLng = new LatLng(currentLat, currentLng);

                                // 지도를 현재 위치로 이동시킵니다.
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
                            }
                        }
                    });
        } else {
            // 권한이 없을 경우 권한을 요청합니다.
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        addMarkersOnMap();
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog, null);
        Builder builder = new Builder(getActivity(), R.style.MyAlertDialogStyle);

        // Find views in the custom layout
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);

        // Set title and message
        dialogTitle.setText(marker.getTitle());
        dialogMessage.setText(marker.getSnippet());

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // "확인" 버튼 클릭 시 동작 추가
            }

        });

        builder.setNegativeButton("상세화면", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // "상세화면" 버튼 클릭 시 동작 추가
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                // Pass marker information to DetailActivity
                intent.putExtra("LAT_LNG", marker.getPosition());
                intent.putExtra("TITLE", marker.getTitle());
                intent.putExtra("SNIPPET", marker.getSnippet());

                // MarkerInfo에서 이미지 리소스 ID를 가져와 전달
                MarkerInfo markerInfo = (MarkerInfo) marker.getTag();
                if (markerInfo != null) {
                    intent.putExtra("IMAGE_RES_ID", markerInfo.getImageResId());
                    intent.putExtra("ADDITIONALINFO", markerInfo.getAdditionalInfo()); // 추가 정보를 가져와 전달
                }

                startActivity(intent);
            }


        });


        builder.setView(dialogView);
        builder.show();
        return true;
    }
}