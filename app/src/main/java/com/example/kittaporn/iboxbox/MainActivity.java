package com.example.kittaporn.iboxbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button see;
    TextView name , result , rest , play , dogName , deviceid;
    ImageView restImage , playImage;
    DatabaseReference db , dbDevice , dbToUser , dbToDevice;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        see = (Button) findViewById(R.id.seemoreBtn);
        name = (TextView) findViewById(R.id.nameText);
        result = (TextView) findViewById(R.id.resultText);
        rest = (TextView) findViewById(R.id.restText);
        play = (TextView) findViewById(R.id.playText);
        restImage = (ImageView) findViewById(R.id.restImage);
        playImage = (ImageView) findViewById(R.id.playImage);

        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView2.getHeaderView(0);

        dogName = (TextView) headerView.findViewById(R.id.dogname);
        deviceid = (TextView) headerView.findViewById(R.id.deviceID);
        uid = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("DOGID");
        dbDevice = FirebaseDatabase.getInstance().getReference("USER").child(uid).child("device");
        dbToUser = FirebaseDatabase.getInstance().getReference("USER").child(uid).child("device");
        dbToDevice = FirebaseDatabase.getInstance().getReference("DOGID");
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DogActivity.class);
                startActivity(i);
            }
        });
        final StackedBarChart mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ////////////////////////////////////////////////////////
        dbToUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String deviceidIndb = dataSnapshot.getValue(String.class);
                deviceid.setText(deviceidIndb);
                dbToDevice.child(deviceidIndb).child("information").child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String dognameIndb = dataSnapshot.getValue(String.class);
                        dogName.setText(dognameIndb);
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
        ///////////////////////////////////////////////////////

        dbDevice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                String deviceID = dataSnapshot.getValue(String.class);

                db.child(deviceID).child("information").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot infoSnapshot) {
                        DogInformationGetter dogInformation = infoSnapshot.getValue(DogInformationGetter.class);
                        name.setText(dogInformation.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                db.child(deviceID).child("DATA").limitToLast(7).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot actSnapshot) {
                        for(DataSnapshot forSnapshot : actSnapshot.getChildren()) {

                            PlayRestGetter playRest = forSnapshot.getValue(PlayRestGetter.class);
                            String date = forSnapshot.getKey();
                            String day = "";

                            for(int i=0 ; i<date.length() ; i++) {
                                if(date.charAt(i) != '-') {
                                    day = day + date.charAt(i);
                                } else {
                                    break;
                                }
                            }

                            StackedBarModel bar = new StackedBarModel(day);
                            bar.addBar(new BarModel(playRest.getPlay() , 0xFF63CBB0));
                            bar.addBar(new BarModel(playRest.getRest() , 0xFF56B7F1));
                            mStackedBarChart.addBar(bar);
                            mStackedBarChart.startAnimation();
                        }
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Activity) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this,DogActivity.class);
            startActivity(i);
        } else if (id == R.id.location) {
            Intent i = new Intent(MainActivity.this,MapRealtime.class);
            startActivity(i);
        } else if (id == R.id.History) {

        } else if (id == R.id.Hospital) {
            Intent i = new Intent(MainActivity.this , MapHospital.class);
            startActivity(i);
        }else if(id == R.id.Setting){
            Intent i = new Intent(MainActivity.this , DogInformation.class);
            i.putExtra("pagenumber",1);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
