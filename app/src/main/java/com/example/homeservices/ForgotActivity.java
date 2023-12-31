package com.example.homeservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    // firebase

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        EditText editEmailAddress = (EditText) findViewById(R.id.editEmailAddress);
        TextView noteView = (TextView) findViewById(R.id.noteView);
        Button forgotButton = (Button) findViewById(R.id.forgotButton);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
            private void resetPassword() {

                String email = editEmailAddress.getText().toString().trim();

                if(email.isEmpty()){
                    editEmailAddress.setError("Email is required!");
                    editEmailAddress.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editEmailAddress.setError("Please provide valid EmailID");
                    editEmailAddress.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                   if (task.isSuccessful()){
                       Toast.makeText(ForgotActivity.this,"Check your email to reset Password!",Toast.LENGTH_LONG).show();
                       noteView.setVisibility(View.VISIBLE);
                       progressBar.setVisibility(View.GONE);


                   }else {
                       Toast.makeText(ForgotActivity.this,"Try again! Something wrong happend!",Toast.LENGTH_LONG).show();
                   }

                    }
                });
            }
        });
    }



    }
