package com.example.travellernotebook.ui.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.travellernotebook.R;
import com.example.travellernotebook.di.components.AppComponent;
import com.example.travellernotebook.di.components.DaggerMainActivityComponent;
import com.example.travellernotebook.di.components.MainActivityComponent;
import com.example.travellernotebook.di.modules.MainModule;
import com.example.travellernotebook.domain.App;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.domain.Location;
import com.example.travellernotebook.ui.trip.SharedTripsListFrgment;
import com.example.travellernotebook.ui.user.SplashFrgment;
import com.example.travellernotebook.util.Constants;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import static com.example.travellernotebook.util.Constants.NOTIFICATION_CHANNEL_ID;


public class MainActivity extends AppCompatActivity {

    public PlacesClient mPlacesClient;
    FragmentManager mFragmentManager;
    Unbinder unbinder;
    Fragment CurrentFragment;
    public Trip activeTrip;
    public Location activeLocation;
    public MainActivityComponent mMainActivityComponent;
    boolean loaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mPlacesClient = Places.createClient(this);
        mFragmentManager = getSupportFragmentManager();
        AppComponent mAppComponent = ((App)getApplication()).getAppComponent();

        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .appComponent(mAppComponent)
                .mainModule(new MainModule())
                .build();


        mAppComponent.provideFirestoreDatabase().collection(Constants.KeyFirebaseFirestoreDocument)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if(!loaded){
                            loaded = true;
                            return;
                        }

                        if (e != null)
                            return;
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                dispalyNotification(getString(R.string.msg_traveller_shared_trip));
                            }
                        }
                    }
                });

        String toSharedTrips = getIntent().getStringExtra(Constants.KeyToSharedTrips);
        if(toSharedTrips==null)
              transitionToFragment(new SplashFrgment());
        else  transitionToFragment(new SharedTripsListFrgment());
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

     public void showMessagePopup(String message){
         AlertDialog mAlertDialog = null;
         AlertDialog.Builder mAlertDialogBuilder=   new AlertDialog.Builder(this);
         mAlertDialogBuilder.setMessage(message);
         mAlertDialogBuilder.setCancelable(true);
         mAlertDialogBuilder.setNegativeButton(R.string.txt_close, (dialogInterface, i) -> dialogInterface.dismiss());
         mAlertDialog =  mAlertDialogBuilder.create();
         mAlertDialog.show();
         Button mButton = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
         mButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
     }


    private void dispalyNotification(String message) {
        NotificationCompat.Builder mBuilder = null;
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtra(Constants.KeyToSharedTrips,Constants.KeyToSharedTrips);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent,
                PendingIntent.FLAG_ONE_SHOT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID+"_name", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mNotificationChannel);
              mBuilder =  new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        }else mBuilder = new NotificationCompat.Builder(this);


        mBuilder.setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mBuilder.build());
    }



}
