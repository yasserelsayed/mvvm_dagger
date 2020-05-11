package com.example.travellernotebook.ui.base.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.domain.TripLocation;
import com.example.travellernotebook.ui.base.MainActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationPickerFragment extends Fragment  implements OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng mCurrenjtLatLng ;
    Marker mMarker;
    MainActivity mMainActivity;
    Geocoder mGeocoder;
    Trip mTrip;
    TripLocation mTripLocation;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    public LocationPickerFragment(Trip mTrip){
        this.mTrip = mTrip;
        if(mTrip.getLatitude()!=null && mTrip.getLatitude()>0)
            mCurrenjtLatLng = new LatLng(mTrip.getLatitude(),mTrip.getLongitude());
        else mCurrenjtLatLng = new LatLng(-34, 151);
    }

    public LocationPickerFragment(TripLocation mTripLocation){
        this.mTripLocation = mTripLocation;
        if(mTripLocation.getLatitude()!=null && mTripLocation.getLatitude()>0)
            mCurrenjtLatLng = new LatLng(mTripLocation.getLatitude(),mTripLocation.getLongitude());
        else mCurrenjtLatLng = new LatLng(-34, 151);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getCurrentLocation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_map, container, false);
        mMainActivity = (MainActivity)getActivity();
        ButterKnife.bind(this,mView);
        mGeocoder = new Geocoder(mMainActivity, Locale.getDefault());
        mapFragment  = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if(place.getLatLng()!=null)
                    setPinLocation(place.getLatLng(), true);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Address> addresses = mGeocoder.getFromLocation(mCurrenjtLatLng.latitude, mCurrenjtLatLng.longitude, 1);
                    if(addresses.size() > 0) {
                        if(mTrip!=null)
                        mTrip.setLocationName(addresses.get(0).getLocality());
                        else if(mTripLocation!=null)
                            mTripLocation.setLocationAddress(addresses.get(0).getLocality());
                    }
                    if(mTrip!=null) {
                        mTrip.setLatitude(mCurrenjtLatLng.latitude);
                        mTrip.setLongitude(mCurrenjtLatLng.longitude);
                    }else if(mTripLocation!=null){
                        mTripLocation.setLatitude(mCurrenjtLatLng.latitude);
                        mTripLocation.setLongitude(mCurrenjtLatLng.longitude);
                    }
                        mMainActivity.onBackPressed();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mMainActivity.CheckPermission( 111, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},  this);
        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMarker==null) {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style));
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.ic_marker));
            mMarker = mMap.addMarker(new MarkerOptions().position(mCurrenjtLatLng).title("").draggable(true).icon(markerIcon));
            setPinLocation(mCurrenjtLatLng, true);
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    mCurrenjtLatLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(mMainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mapFragment.getMapAsync(this);
            return;
        }else {
            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.builder(Arrays.asList(Place.Field.ID, Place.Field.NAME)).build();
            mMainActivity.mPlacesClient.findCurrentPlace(request).addOnSuccessListener(((response) -> {
                if (response.getPlaceLikelihoods().size() > 0) {
                    PlaceLikelihood placeLikelihood = response.getPlaceLikelihoods().get(0);
                    Place place = placeLikelihood.getPlace();
                    LatLng mLatLng = place.getLatLng();
                    if(mLatLng!=null)
                    mCurrenjtLatLng = mLatLng;
                    mapFragment.getMapAsync(this);
                }
            }));
        }
    }


    private void setPinLocation(LatLng mLatLng , boolean MoveCam) {
        if (mMap != null) {
            mMarker.setPosition(mLatLng);
            mCurrenjtLatLng = mLatLng;
            if (MoveCam) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 10));
            }

        }
    }
}
