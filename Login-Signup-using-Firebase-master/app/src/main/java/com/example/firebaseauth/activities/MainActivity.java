package com.example.firebaseauth.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.firebaseauth.R;
import com.example.firebaseauth.adapters.TabsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabsAdapter tabsAdapter;
    private FloatingActionButton floatingActionButton;
    private static final String[] tabTitles = {"Panels", "Tab 2", "Profile"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logFirebaseToken();

        initViews();

        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddPanelActivity.class)));

        final TabLayout tabLayout = findViewById(R.id.main_activity_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }

    private void initViews() {
        floatingActionButton = findViewById(R.id.floating_action_button);
        viewPager = findViewById(R.id.main_activity_view_pager);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(tabsAdapter);
    }

    private void logFirebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener((task) -> {
                    if (!task.isSuccessful()) {
                        Log.w("getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    // Log and toast
                    String msg = "Retrieved token: " + token;
                    Log.d("TOKEN", "Retrieved token: " + msg);
                });
    }
}