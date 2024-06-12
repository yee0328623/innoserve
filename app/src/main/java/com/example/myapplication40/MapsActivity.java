package com.example.myapplication40;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    FrameLayout map;
    GoogleMap gMap;
    Location currentLocation;
    Marker marker1;
    FusedLocationProviderClient fusedClient;
    private static final int REQUEST_CODE = 101;

    FloatingActionButton td_Button;

    Button btn6;
    private Button weatherBtn;
    private Button waterBtn;
    private MarkerOptions markerOptions1;
    private int asd,abc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        map = findViewById(R.id.map);

        weatherBtn = findViewById(R.id.btn4);

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, WeatherDataActivity.class);
                startActivity(intent);
            }
        });

        waterBtn = findViewById(R.id.btn5);
        waterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, WaterDataActivity.class);
                startActivity(intent);
            }
        });

        btn6 = findViewById(R.id.btn6);

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it5 = new Intent(MapsActivity.this, MainActivity8.class);
                startActivity(it5);
            }
        });

        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        startLocationUpdates();


        td_Button = findViewById(R.id.tdButton);

        td_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLocation != null) {
                    double lat1 = currentLocation.getLatitude();
                    double lon1 = currentLocation.getLongitude();
                    LatLng fixedLocation = new LatLng(25.021858015926703, 121.53529975334598);
                    LatLng fixedLocationwaring = new LatLng(25.020468996722776,  121.5376966266158);
                    double lat2 = fixedLocation.latitude;
                    double lon2 = fixedLocation.longitude;
                    double latwaring = fixedLocationwaring.latitude;
                    double lonwaring = fixedLocationwaring.longitude;
                    double dist = distancesafe(lat1, lon1, lat2, lon2);
                    double distwaring = distancewaring(lat1, lon1, latwaring, lonwaring);
                    String distStr = String.format("%.2f", dist); // 格式化距離值為字串
                    String distStrwaring = String.format("%.2f", distwaring);
                    if(distwaring<dist) {
                        if (distwaring <= 100) {
                            new AlertDialog.Builder(MapsActivity.this)
                                    .setIcon(R.drawable._291745)
                                    .setTitle("你已經在危險水域 (距離: " + distStrwaring + " 公尺)")
                                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle button click
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        }
                        else {
                            new AlertDialog.Builder(MapsActivity.this)
                                    .setIcon(R.drawable._291745)
                                    .setTitle("你目前在安全區域")
                                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle button click
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        }
                    }
                    else{
                        new AlertDialog.Builder(MapsActivity.this)
                                .setIcon(R.drawable._291745)
                                .setTitle("你目前在安全區域")
                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Handle button click
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    }

                }
            }
        });
    }



    private double distancesafe(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344; // 转换为米
        return dist;
    }
    private double distancewaring(double lat1, double lon1, double latWarning, double lonWarning) {
        double theta = lon1 - lonWarning;
        double distWarning = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(latWarning)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(latWarning)) *
                        Math.cos(deg2rad(theta));
        distWarning = Math.acos(distWarning);
        distWarning = rad2deg(distWarning);
        distWarning = distWarning * 60 * 1.1515 * 1609.344; // 转换为米
        return distWarning;
    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    currentLocation = location;
                    //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }

            }
        });
    }
    private final int LOCATION_UPDATE_INTERVAL = 500; // 更新间隔时间，单位为毫秒
    private final int ALERT_COOLDOWN_TIME = 60000; // 冷却时间，单位为毫秒

    private Handler locationUpdateHandler = new Handler();
    private long lastAlertTime = 0;

    private Runnable locationUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            getLocation(); // 定期获取位置信息

            if (currentLocation != null) {
                double lat1 = currentLocation.getLatitude();
                double lon1 = currentLocation.getLongitude();
                LatLng fixedLocation = new LatLng(25.021858015926703, 121.53529975334598);
                LatLng fixedLocationwaring = new LatLng(25.020468996722776,  121.5376966266158);
                double lat2 = fixedLocation.latitude;
                double lon2 = fixedLocation.longitude;
                double latwaring = fixedLocationwaring.latitude;
                double lonwaring = fixedLocationwaring.longitude;
                double distwaring = distancewaring(lat1, lon1, latwaring, lonwaring);
                String distStrwaring = String.format("%.2f", distwaring);
                long currentTimeMillis= System.currentTimeMillis();
                if(distwaring <= 100 && (currentTimeMillis - lastAlertTime > ALERT_COOLDOWN_TIME)) {
                    lastAlertTime= currentTimeMillis;
                    new AlertDialog.Builder(MapsActivity.this)
                            .setIcon(R.drawable._291745)
                            .setTitle("你已經在危險水域 (距離: " + distStrwaring + " 公尺)")
                            .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Handle button click
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                }
                else if(abc==0){
                    abc = abc + 1;
                    new AlertDialog.Builder(MapsActivity.this)
                            .setIcon(R.drawable._291745)
                            .setTitle("你目前在安全區域")
                            .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Handle button click
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                }
            }
            locationUpdateHandler.postDelayed(this, LOCATION_UPDATE_INTERVAL);
        }
    };

    private void startLocationUpdates() {
        locationUpdateHandler.postDelayed(locationUpdateRunnable, LOCATION_UPDATE_INTERVAL);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("目前所在位置")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        // 先清除之前的标记
        if (marker1 != null) {
            marker1.remove();
        }

        // Add the new marker
        marker1 = googleMap.addMarker(markerOptions);

        // 添加固定点的标记
        LatLng fixedLocationwaring = new LatLng(25.020468996722776,  121.5376966266158);
        MarkerOptions fixedMarkerOptionswaring = new MarkerOptions()
                .position(fixedLocationwaring)
                .title("危險地點"); // 自定义标记的标题
        googleMap.addMarker(fixedMarkerOptionswaring);
        // Move the camera to the new location

        if(asd==0) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            asd = 1;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

}