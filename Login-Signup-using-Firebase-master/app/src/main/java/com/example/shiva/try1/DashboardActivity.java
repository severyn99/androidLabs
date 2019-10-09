package com.example.shiva.try1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvEmail;
    private Button btnLogout;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tvEmail = (TextView) findViewById(R.id.text_welcome);
        btnLogout = (Button) findViewById(R.id.logout_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
        tvEmail.setText(tvEmail.getText().toString() + user.getDisplayName());
        logOut();

    }

    private void logOut() {
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
                Toast.makeText(DashboardActivity.this, R.string.logout_status, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

}