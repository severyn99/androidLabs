package com.example.shiva.try1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button LogInButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseUser mUser;
    private String email;
    private String password;
    private ProgressDialog dialog;
    private TextView newUser;
    private TextView passwordValidResult;
    private TextView emailValidResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogInButton = (Button) findViewById(R.id.button_login);
        Email = (EditText) findViewById(R.id.edit_email);
        Password = (EditText) findViewById(R.id.edit_password);
        newUser = (TextView) findViewById(R.id.button_register);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        emailValidResult = (TextView) findViewById(R.id.validate_email);
        passwordValidResult = (TextView) findViewById(R.id.validate_password);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSign();
            }
        });

        createNewUser();
    }

    private void createNewUser() {
        newUser.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    private void userSign() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailValidResult.setText("Email is required");
            return;
        } else if (!isValidEmail(email)) {
            emailValidResult.setText("Email is not valid");
            return;
        } else if (password.length() < 8) {
            passwordValidResult.setText("Password must be more than 8 characters long");
            return;
        }

        dialog.setMessage("Loging in please wait...");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(Login.this, "Login not successfull", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Intent intent = new Intent(Login.this, DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}

