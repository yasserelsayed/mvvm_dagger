package com.example.travellernotebook.ui.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.example.travellernotebook.R;
import com.example.travellernotebook.di.components.AppComponent;
import com.example.travellernotebook.domain.App;
import com.example.travellernotebook.ui.profile.views.ProfileFrgment;
import com.example.travellernotebook.ui.trip.views.HomeFrgment;
import com.example.travellernotebook.ui.trip.views.TripFrgment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnAdd)
    public FloatingActionButton btnAdd;
    public PlacesClient mPlacesClient;
    public AppComponent mAppComponent;
    FragmentManager mFragmentManager;
    Unbinder unbinder;
    Fragment CurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mPlacesClient = Places.createClient(this);
        mFragmentManager = getSupportFragmentManager();
        mAppComponent = ((App)getApplication()).getAppComponent();
        transitionToFragment(new HomeFrgment());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionToFragment(new TripFrgment());
            }
        });
//        FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//// Add a new document with a generated ID
//        mFirebaseFirestore.collection("trips")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("", "Error adding document", e);
//                    }
//                });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuProfile:
                transitionToFragment(new ProfileFrgment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void transitionToFragment(Fragment mFragment)
    {
        CurrentFragment = mFragment;
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frmFragmentContainer,mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CurrentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void CheckPermission(int requestCode, @NonNull String[] permissions, Fragment _Fragment)
    {
        int result = ContextCompat.checkSelfPermission(this,permissions[0]);
        if (result != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        else CurrentFragment.onRequestPermissionsResult(requestCode, permissions, new int[]{result});
     }



}
