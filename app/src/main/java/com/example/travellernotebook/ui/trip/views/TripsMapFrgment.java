package com.example.travellernotebook.ui.trip.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import butterknife.ButterKnife;

public class TripsMapFrgment extends Fragment implements OnMapReadyCallback {

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    List<Trip> lstTrips;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_map_items,container,false);
         ButterKnife.bind(this,mView);
         MainActivity mMainActivity =((MainActivity) getActivity());
        mapFragment  = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
         TripViewModel mTripViewModel = new ViewModelProvider(mMainActivity,mTripViewModelsFactory).get(TripViewModel.class);
         mTripViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
              @Override
               public void onChanged(List<Trip> trips) {
                  lstTrips = trips;
                  mapFragment.getMapAsync(TripsMapFrgment.this);
              }
            });

        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style));
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.ic_marker));
            for(Trip mTrip:lstTrips)
            mMap.addMarker(new MarkerOptions().position(new LatLng(mTrip.getLatitude(),mTrip.getLongitude())).title(mTrip.getTripName()).icon(markerIcon));

            if(lstTrips.size() >0){
                Trip mTrip = lstTrips.get(0);
                LatLng mLatLng =  new LatLng(mTrip.getLatitude(),mTrip.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 10));
            }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
