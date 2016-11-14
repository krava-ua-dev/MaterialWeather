package com.krava.dagger2test.presentation.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.krava.dagger2test.R;
import com.krava.dagger2test.presentation.view.activity.MainActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;

/**
 * Created by krava2008 on 01.11.16.
 */

public class DetectCityFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final int REQUEST_CHECK_SETTINGS = 12;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private TextView cityName;
    private String city;
    private CityDetectionListener detectionListener;

    public interface CityDetectionListener {
        void onCityDetected(String cityName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof CityDetectionListener){
            this.detectionListener = (CityDetectionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.choose_city_fragment, container, false);

        cityName = (TextView) view.findViewById(R.id.detected_city);

        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detectionListener != null) {
                    detectionListener.onCityDetected(city);
                }else{
                    showSnackbarMessage(getView(), "detectionListener null");
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


    }

    public void onGpsRequestResult(boolean result) {
        showSnackbarMessage(getView(), "gps result: " + result);

        if(result) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(getActivity())
                         .request(Manifest.permission.ACCESS_FINE_LOCATION)
                         .subscribe(new Subscriber<Boolean>() {
                             @Override
                             public void onCompleted() {

                             }

                             @Override
                             public void onError(Throwable e) {

                             }

                             @Override
                             public void onNext(Boolean garanted) {
                                 if (garanted) {
                                     LocationServices.FusedLocationApi.requestLocationUpdates(
                                             mGoogleApiClient, mLocationRequest, DetectCityFragment.this);
                                 } else {

                                 }
                             }
                         });
        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, DetectCityFragment.this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(Build.VERSION.SDK_INT >= 23) {
            RxPermissions.getInstance(getActivity())
                         .request(Manifest.permission.ACCESS_FINE_LOCATION)
                         .subscribe(new Subscriber<Boolean>() {
                             @Override
                             public void onCompleted() {

                             }

                             @Override
                             public void onError(Throwable e) {

                             }

                             @Override
                             public void onNext(Boolean garanted) {
                                 if(garanted) {
                                     checkLocationAtStart();
                                 }else{
                                    //user has no permission
                                 }
                             }
                         });
        }else{
            checkLocationAtStart();
        }

    }


    private void checkLocationAtStart() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(mLastLocation == null) {
            checkGps();
        }else{
            calculateCityName();
        }
    }

    private void calculateCityName() {
        Geocoder geoCoder = new Geocoder(getContext(), Locale.ENGLISH);
        try {
            List<Address> addresses = geoCoder.getFromLocation(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude(), 1);

            if (addresses.size() > 0) {
                city = String.format("%s, %s", addresses.get(0).getLocality(), addresses.get(0).getCountryCode());
            }else{
                city = "Unavailable";
            }
            cityName.setText(city);
            TransitionManager.beginDelayedTransition((CoordinatorLayout)getView());
            getView().findViewById(R.id.city_layout).setVisibility(View.VISIBLE);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void checkGps(){
        if(!((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult result1) {
                    final Status status = result1.getStatus();
//                final LocationSettingsStates = result1.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location requests here.

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        getActivity(),
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the settings so we won't show the dialog.

                            break;
                    }
                }
            });
        }else{
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        int i = 0;
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            mLastLocation = location;
            stopLocationUpdates();
            calculateCityName();
        }
    }
}
