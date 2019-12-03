package com.example.cs4322.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs4322.R;
import com.example.cs4322.ui.login.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private FirebaseUser user;
    private String userID;

    private TextView emailTxt, usernameTxt, passwordTxt, newPasswordTxt;

    private Button deleteBtn, locationBtn;
    private FloatingActionButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();

            }
        };

        passwordTxt = findViewById(R.id.passwordText);
        emailTxt = findViewById(R.id.emailText);
        usernameTxt = findViewById(R.id.userNameText);
        newPasswordTxt = findViewById(R.id.newPasswordTxt);
        deleteBtn = findViewById(R.id.deleteAccountBtn);
        saveBtn = findViewById(R.id.saveBtn);
        locationBtn = findViewById(R.id.locationBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameTxt.getText().toString() != null && !usernameTxt.getText().toString().equals("")) {
                    UserProfileChangeRequest nameUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(usernameTxt.getText().toString())
                            .build();

                    user.updateProfile(nameUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(AccountActivity.this, "Name change failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (emailTxt.getText().toString() != null && !emailTxt.getText().toString().equals("")) {
                    user.updateEmail(emailTxt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(AccountActivity.this, "Not a valid Email!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (newPasswordTxt.getText().toString() != null && !newPasswordTxt.getText().toString().equals("")) {
                    reauth();
                    user.updatePassword(newPasswordTxt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Nothing
                        }
                    });
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reauth();

                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mRef.child(userID).removeValue();

                            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent map = new Intent(AccountActivity.this, MapActivity.class);
                startActivity(map);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        usernameTxt.setText(user.getDisplayName());
        emailTxt.setText(user.getEmail());
    }

    public void reauth() {
        String oldPass;
        String email;

        if (passwordTxt.getText().toString() != null && !passwordTxt.getText().toString().equals(""))
            oldPass = passwordTxt.getText().toString();
        else
            oldPass = "0";

        if (emailTxt.getText().toString() != null && !emailTxt.getText().toString().equals(""))
            email = emailTxt.getText().toString();
        else
            email = "0";

        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPass);
        mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful())
                    Toast.makeText(AccountActivity.this, "Current Password is incorrect!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
