package com.example.shiva.try1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText password;
    private Button registerbtn;
    private TextView loginPageBack;
    private FirebaseAuth mAuth;
    private String Name;
    private String Phone;
    private String Email;
    private String Password;
    private ProgressDialog mDialog;
    private TextView passwordValidResult;
    private TextView emailValidResult;
    private TextView phoneValidResult;
    private TextView nameValidResult;
    final static int passwordLength = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.edit_name);
        phone = findViewById(R.id.edit_number);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        registerbtn = findViewById(R.id.button_register);
        loginPageBack = findViewById(R.id.button_login);

        emailValidResult = findViewById(R.id.validate_email);
        passwordValidResult = findViewById(R.id.validate_password);
        phoneValidResult = findViewById(R.id.validate_phone);
        nameValidResult = findViewById(R.id.validate_name);

        mAuth = FirebaseAuth.getInstance();
        registerbtn.setOnClickListener(this);
        loginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public void onClick(View v) {
        if (v == registerbtn) {
            registerUser();
        } else if (v == loginPageBack) {
            startActivity(new Intent(Register.this, Login.class));
        }
    }

    private void registerUser() {
        Name = name.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            emailValidResult.setText(R.string.email_required);
            return;
        } else if (!isValidEmail(Email)) {
            emailValidResult.setText(R.string.email_invalid);
            return;
        } else if (Password.length() < passwordLength) {
            passwordValidResult.setText(R.string.short_password);
            return;
        } else if (TextUtils.isEmpty(Phone)) {
            phoneValidResult.setText(R.string.phone_required);
            return;
        } else if (TextUtils.isEmpty(Name)) {
            nameValidResult.setText(R.string.email_required);
            return;
        }

        mDialog.setMessage(getString(R.string.creating_user));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(Name)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Register.this, DashboardActivity.class);
                                    mDialog.dismiss();
                                    startActivity(intent);

                                }
                            }
                        });
            }
        });
    }

    public boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}