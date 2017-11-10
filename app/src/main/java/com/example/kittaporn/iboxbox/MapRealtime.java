package com.example.kittaporn.iboxbox;

import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapRealtime extends FragmentActivity implements OnMapReadyCallback {
    DatabaseReference myRef,db;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_realtime);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference("USER").child(user.getUid()).child("device");
        db = FirebaseDatabase.getInstance().getReference("DOGID");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
////54545454

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String device = dataSnapshot.getValue(String.class);
                    db.child(device).child("REALLOCATION").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            LocationGetter locate = dataSnapshot.getValue(LocationGetter.class);
                            LatLng mylocation = new LatLng(locate.getLat(),locate.getLng());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//                LocationGetter locate = dataSnapshot.getValue(LocationGetter.class);
//                LatLng mylocation = new LatLng(locate.getLat(),locate.getLng());
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,
//                        15));
                //mMap.addMarker(new MarkerOptions().position(mylocation).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_dog_round)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String device = dataSnapshot.getValue(String.class);
                db.child(device).child("REALLOCATION").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        LocationGetter locate = dataSnapshot.getValue(LocationGetter.class);
                        LatLng mylocation = new LatLng(locate.getLat(),locate.getLng());
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(mylocation).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_dog_round)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
