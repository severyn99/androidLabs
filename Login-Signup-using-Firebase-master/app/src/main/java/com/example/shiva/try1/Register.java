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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText name,phone,email,password;
    private  Button mRegisterbtn;
    private  TextView mLoginPageBack;
    private  FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    private String Name,Phone,Email,Password;
    private ProgressDialog mDialog;
    private TextView passwordValidResult,emailValidResult,phoneValidResult,nameValidResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.editName);
        phone = (EditText)findViewById(R.id.editNumber);
        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);
        mRegisterbtn = (Button)findViewById(R.id.buttonRegister);
        mLoginPageBack = (TextView)findViewById(R.id.buttonLogin);


        emailValidResult = (TextView) findViewById(R.id.validateEmail);
        passwordValidResult = (TextView)findViewById(R.id.validatePassword);
        phoneValidResult = (TextView) findViewById(R.id.validatePhone);
        nameValidResult = (TextView)findViewById(R.id.validateName);

        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

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
        if (v==mRegisterbtn){
            UserRegister();
        }else if (v== mLoginPageBack){
        startActivity(new Intent(Register.this,login.class));
        }
    }

    private void UserRegister() {
        Name = name.getText().toString().trim();
        Phone = phone.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            emailValidResult.setText("Email is required");
            return;
        } else if (!isValidEmail(Email)) {
            emailValidResult.setText("Email is not valid");
            return;
        } else if (Password.length() < 8) {
            passwordValidResult.setText("Password must be more than 8 characters long");
            return;
        } else if (TextUtils.isEmpty(Phone)) {
            phoneValidResult.setText("Phone is required");
            return;
        } else if (TextUtils.isEmpty(Name)) {
            nameValidResult.setText("Email is required");
            return;
        }


        mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}
