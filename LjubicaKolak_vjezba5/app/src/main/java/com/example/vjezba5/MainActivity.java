package com.example.vjezba5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CHECK_SETTINGS = 101;
    TextView longitude, latitude, address, city, country;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestLocationPermissions();

        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        address = findViewById(R.id.adress);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //posljednja lokacija
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Toast.makeText(MainActivity.this, "Koordinate zadnje lokacije: " +
                                    location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkAndRequestLocationPermissions() {
        ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                    if (fineLocationGranted != null && fineLocationGranted) {
                        Toast.makeText(this, "Odobren pristup preciznoj lokaciji", Toast.LENGTH_SHORT).show();
                        setupLocationTracking();
                    }
                    else if (coarseLocationGranted != null && coarseLocationGranted) {
                        Toast.makeText(this, "Odobren pristup približnoj lokaciji", Toast.LENGTH_SHORT).show();
                        setupLocationTracking();
                    }
                    else {
                        Toast.makeText(this, "Pristup lokaciji nije odobren", Toast.LENGTH_SHORT).show();
                    }
        });
        //proces trazenja dozvola
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @SuppressLint("MissingPermission")
    private void setupLocationTracking() {
        geocoder = Geocoder.isPresent() ? new Geocoder(this) : null;

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateLocationViews(location);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, null);
    }

    private void updateLocationViews(Location location) {
        longitude.setText(Double.toString(location.getLongitude()));
        latitude.setText(Double.toString(location.getLatitude()));

        if (geocoder != null) {
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (addresses.size() > 0) {
                    Address address2 = addresses.get(0);
                    address.setText(address2.getThoroughfare() + " " + address2.getFeatureName() + " " + address2.getLocality());
                    city.setText(address2.getLocality());
                    country.setText(address2.getCountryName());

                }

            }
            catch (IOException e) {
                Log.d("Greška pilikom dohvaćanja podataka o lokaciji", new RuntimeException(e).toString());
            }
        }
    }

    //učestalost osvjezavanja
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}
