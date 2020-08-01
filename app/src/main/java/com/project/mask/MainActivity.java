package com.project.mask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest; //강원도 춘천시 효자동
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFrag;
    Menu2Fragment menu2Fragment;
    Sub2Fragment sub2Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrag = new MainFragment();
        menu2Fragment = new Menu2Fragment();
        sub2Fragment = new Sub2Fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFrag).commit();

        //Navigation 클릭 시 화면 이동
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.tab1:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFrag).commit();
                            return true;
                        case R.id.tab2:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, menu2Fragment).commit();
                            return true;
                        case R.id.tab3:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, sub2Fragment).commit();
                            return true;
                    }
                    return false;
                }
            }
        );

        try {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Kakao 플랫폼 Key값 알아오기
    public void findByKeyhash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature: info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}


