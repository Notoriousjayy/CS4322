package com.example.cs4322.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs4322.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignUp;
    TextView tvSignIn;
    TextView errorText;
    FirebaseAuth mFirebaseAuth;
    private String email;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.username);
        password = findViewById(R.id.password);
        tvSignIn = findViewById(R.id.textView3);
        btnSignUp = findViewById(R.id.login);
        errorText = findViewById(R.id.errorText);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailId.getText().toString();
                pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    //Toast.makeText(MainActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                    errorText.setText("Fields are Empty!");
                    errorText.setVisibility(View.VISIBLE);
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                //Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                                errorText.setText("SignUp Unsuccessful, Please Try Again");
                                errorText.setVisibility(View.VISIBLE);
                            }
                            else {
                                Intent intoHome = new Intent(MainActivity.this, HomeActivity.class);
                                intoHome.putExtra("extra", email);
                                startActivity(intoHome);
                            }
                        }
                    });
                }
                else{
                    //Toast.makeText(MainActivity.this, "Error Ocurred!", Toast.LENGTH_SHORT).show();
                    errorText.setText("Error Occured, Please Try Again");
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
