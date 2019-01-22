package com.example.peter.mob403_asm;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Fragment activeFragment;
    HomeFragment homeFragment = null;
    KindFragment kindFragment = null;
    LibraryFragment libraryFragment = null;
    ProfileFragment profileFragment = null;
    FragmentManager fragmentManager;

    public static boolean reloadLibrary = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        activeFragment = homeFragment;
        fragmentManager = getSupportFragmentManager();

        loadFragment(activeFragment);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .add(R.id.fragment_container, homeFragment)
                            .commit();
                }else{

                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(homeFragment)
                            .commit();
                    activeFragment = homeFragment;
                }
                break;

            case R.id.navigation_theloai:
                if(kindFragment == null){
                    kindFragment = new KindFragment();
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .add(R.id.fragment_container, kindFragment)
                            .commit();
                    activeFragment = kindFragment;
                }else{

                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(kindFragment)
                            .commit();
                    activeFragment = kindFragment;
                }
                break;

            case R.id.navigation_library:
                if(libraryFragment == null){
                    libraryFragment = new LibraryFragment();
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .add(R.id.fragment_container, libraryFragment)
                            .commit();
                    activeFragment = libraryFragment;
                }else{

                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(libraryFragment)
                            .commit();
                    activeFragment = libraryFragment;
                    if(reloadLibrary){// gio truyen
                        MyDatabase db = new MyDatabase(MainActivity.this);
                        TokenAndUserId tokenAndUserId = db.getTokenAndUserId();
                        //reloadLibrary = true;
                        libraryFragment.loadJSON(tokenAndUserId.getToken());

                    }
                }
                break;

            case R.id.navigation_profile:
                if(profileFragment == null){
                    profileFragment = new ProfileFragment();
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .add(R.id.fragment_container, profileFragment)
                            .commit();
                    activeFragment = profileFragment;
                }else{

                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(profileFragment)
                            .commit();
                    activeFragment = profileFragment;
                }
                break;
        }
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
