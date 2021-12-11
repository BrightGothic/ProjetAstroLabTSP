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


public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signin);

        EditText email = (EditText) findViewById(R.id.activity_singin_edittext_name);
        EditText password = (EditText) findViewById(R.id.activity_signin_edittext_pw);
        this.email = email.getText().toString();
        this.password = password.getText().toString();

        Button signin = (Button) findViewById(R.id.activity_singin_button_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Yo");
                signIn(email.getText().toString(), password.getText().toString());

            }
        });

        Button sinscrire = (Button) findViewById(R.id.activity_signin_button_signup);
        sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Yo");
                switchActivitiesSigninintoSignup();
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
            switchActivitiesSigninintoHome();
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
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }




    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            setContentView(R.layout.activity_home);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                       //     checkForMultiFactorFailure(task.getException());
                        }

                        if (!task.isSuccessful()) {
                      //      mBinding.status.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    private void switchActivitiesSigninintoSignup() {
        Intent switchActivityIntent = new Intent(this, SignUpActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchActivitiesSigninintoHome(){
        Intent switchActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(switchActivityIntent);
    }

}
