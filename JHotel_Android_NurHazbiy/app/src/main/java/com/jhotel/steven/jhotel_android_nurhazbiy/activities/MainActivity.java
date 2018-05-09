package com.jhotel.steven.jhotel_android_nurhazbiy.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.jhotel.steven.jhotel_android_nurhazbiy.Fragment.HomeFragment;
import com.jhotel.steven.jhotel_android_nurhazbiy.Fragment.OrderFragment;
import com.jhotel.steven.jhotel_android_nurhazbiy.Fragment.ProfileFragment;
import com.jhotel.steven.jhotel_android_nurhazbiy.Object.Hotel;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;
import com.jhotel.steven.jhotel_android_nurhazbiy.Object.Room;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends FragmentActivity {
    private BottomNavigationView mainNav;
    private FrameLayout mainLayout;
    private OrderFragment orderFragment;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    private ArrayList<Hotel> listHotel;
    private ArrayList<Room> listRoom ;
    private HashMap<Hotel ,ArrayList<Room>> childMapping ;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    private int currentUserId;

    /*TODO refresh vacant rooms list whenever activity started
    @Override
    protected void onStart(){
        super.onStart();
        refreshList();

        //TODO check session if done or not
        checkSession();
    }
*/

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (FrameLayout) findViewById(R.id.main_frame);
        mainNav = (BottomNavigationView) findViewById(R.id.main_navbar);

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        profileFragment = new ProfileFragment();

        if (savedInstanceState == null) {
            currentUserId = getIntent().getIntExtra("currentUserId",0);
        } else {
            currentUserId= (int) savedInstanceState.getSerializable("currentUserId");
        }

        //System.out.println(currentUserId);
        //TODO passing currentUserId value
        Bundle bundle = new Bundle();
        bundle.putInt("currentUserId",currentUserId);
        orderFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);

        //TODO set default fragment
        if(findViewById(R.id.main_frame) != null){
            if(savedInstanceState != null){
                return;
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.main_frame,homeFragment,null);
            fragmentTransaction.commit();
        }

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_order:
                        setFragment(orderFragment);
                        return true;

                    case R.id.nav_profile:
                        setFragment(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }
}

