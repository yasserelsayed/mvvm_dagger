package com.example.travellernotebook.ui.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.travellernotebook.R;
import com.example.travellernotebook.di.components.AppComponent;
import com.example.travellernotebook.domain.App;
import com.example.travellernotebook.ui.base.views.PagerAdapter;
import com.example.travellernotebook.ui.profile.views.ProfileFrgment;
import com.example.travellernotebook.ui.trip.views.TripFrgment;
import com.example.travellernotebook.ui.trip.views.TripsListFrgment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.group)
    Group mGroup;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrnTabs)
    TabLayout scrnTabs;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

   public AppComponent mAppComponent;


    Unbinder unbinder;
    FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mGroup.setVisibility(View.VISIBLE);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFragmentManager = getSupportFragmentManager();
        PagerAdapter mPagerAdapter = new PagerAdapter(mFragmentManager);
        pager.setAdapter(mPagerAdapter);
        scrnTabs.setupWithViewPager(pager);
        mAppComponent = ((App)getApplication()).getAppComponent();
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
        mGroup.setVisibility(View.GONE);
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frmFragmentContainer,mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }


}
