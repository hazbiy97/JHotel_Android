package com.jhotel.steven.jhotel_android_nurhazbiy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.SessionManager;
import com.jhotel.steven.jhotel_android_nurhazbiy.fragment.HomeFragment;
import com.jhotel.steven.jhotel_android_nurhazbiy.fragment.OrderFragment;
import com.jhotel.steven.jhotel_android_nurhazbiy.fragment.ProfileFragment;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Hotel;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  This class is used for creating Main activity on application that contains
 *  Profile menu, vacant room lists, order menu
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class MainActivity extends AppCompatActivity {
    // instance vaiable
    private BottomNavigationView mainNav;
    private FrameLayout mainLayout;
    private OrderFragment orderFragment;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private Bundle bundle;
    private int currentUserId;

    // Session Manager Class
    private SessionManager session;

    /**
     * This method is used to override onBackPressed method. This method will close application
     * when user pressing back button so the application didn't call activity from stackActivity
     * (hence calling LoginActivity when user pressing back button after logging in without
     * logging out first)
     */
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    /**
     * This method is used to do things when the activity is resumed
     */
    @Override
    protected void onResume(){
        super.onResume();

        session.checkLogin();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("JHotelPref", 0);

        currentUserId = pref.getInt("id",0);

        // passing currentUserId
        bundle = new Bundle();
        bundle.putInt("currentUserId",currentUserId);

        System.out.println(currentUserId);
    }

    /**
     * This method is used to do things when the activity is created. Used to generate layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        // generate layout
        mainLayout = (FrameLayout) findViewById(R.id.main_frame);
        mainNav = (BottomNavigationView) findViewById(R.id.main_navbar);

        // generate fragment
        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        profileFragment = new ProfileFragment();

        // set default fragment, and that is MainFragment
        if(findViewById(R.id.main_frame) != null){
            if(savedInstanceState != null){
                return;
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.main_frame,homeFragment,null);
            fragmentTransaction.commit();
        }

        // set bottom navigation bar behaviour
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bundle.putInt("lastMenu", mainNav.getSelectedItemId());
                orderFragment.setArguments(bundle);
                homeFragment.setArguments(bundle);
                profileFragment.setArguments(bundle);

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

    /**
     * This method is used to change fragment in main activity
     *
     * @param fragment new fragment that will be shown
     */
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    /**
     * This method is used to change fragment from fragment without using navigation bar
     *
     * @param menuId fragment's id that will be shown
     */
    public void changeMenu(int menuId){
        mainNav.setSelectedItemId(menuId);
    }

    /**
     * This method is used to get session activity (for fragments)
     *
     * @return session return session from this activity
     */
    public SessionManager getSession(){
        return this.session;
    }
}

