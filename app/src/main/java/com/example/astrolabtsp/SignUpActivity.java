package com.example.astrolabtsp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signup);

        EditText email = (EditText) findViewById(R.id.activity_signup_edittext_name);
        EditText password = (EditText) findViewById(R.id.activity_signup_edittext_pw);
        this.email = email.getText().toString();
        this.password = password.getText().toString();

        Button button_createuser = (Button) findViewById(R.id.activity_signup_button_signup);
        button_createuser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser(); //You Firebase user
                            // user registered, start profile activity
                            Toast.makeText(SignUpActivity.this,"Account Created",Toast.LENGTH_LONG).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                        }
                        else{
                            if (!task.isSuccessful()) {
                                Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                            }
                            Toast.makeText(SignUpActivity.this,"Could not create account. Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            });

        Button button_signin = (Button) findViewById(R.id.actiivity_signup_button_signin);
        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Yo");
                switchActivitiesSignupintoSignin();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    //    updateUI(currentUser);
    }



    //Change UI according to user data.
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,HomeActivity.class));

        }else {
            Toast.makeText(this,"You Didnt signed in",Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }


    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void switchActivitiesSignupintoSignin() {
        Intent switchActivityIntent = new Intent(this, SignInActivity.class);
        startActivity(switchActivityIntent);
    }


}
