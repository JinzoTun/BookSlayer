package com.jinzo.mirage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editTextEmailSignUp);
        passwordEditText = findViewById(R.id.editTextPasswordSignUp);

        Button signUpButton = findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                createAccount(email, password);
            }
        });

        TextView loginBt = findViewById(R.id.loginBt);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User creation successful, navigate to the WelcomeActivity
                            startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
                            finish();
                        } else {
                            // If sign up fails, display a message to the user.
                            // You can parse the exception to determine the specific error.
                            Exception exception = task.getException();
                            String errorMessage = "Sign up failed. Please try again.";

                            if (exception != null) {
                                // You can customize error messages based on the exception type.
                                errorMessage = getErrorMessage(exception);
                            }

                            // Update the UI to inform the user about the error
                            showToast(errorMessage);
                        }
                    }
                });
    }

    private String getErrorMessage(Exception exception) {
        // You can customize error messages based on the exception type.
        // This is a basic example, and you might want to handle different exceptions separately.
        if (exception.getMessage() != null && exception.getMessage().contains("email address is already in use")) {
            return "Email address is already in use. Please use a different email.";
        } else {
            return "Sign up failed. Please try again.";
        }
    }

    private void showToast(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
