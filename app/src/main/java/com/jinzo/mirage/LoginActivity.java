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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editTextEmailLogin);
        passwordEditText = findViewById(R.id.editTextPasswordLogin);

        Button loginButton = findViewById(R.id.buttonLogin);
        TextView signupButton = findViewById(R.id.buttonSignup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                signIn(email, password);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in successful, navigate to the WelcomeActivity
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            // You can parse the exception to determine the specific error.
                            Exception exception = task.getException();
                            String errorMessage = "Authentication failed. Please try again.";

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
        if (exception.getMessage() != null && exception.getMessage().contains("password is invalid")) {
            return "Invalid password. Please try again.";
        } else if (exception.getMessage() != null && exception.getMessage().contains("no user record")) {
            return "No user found with this email. Please sign up.";
        } else {
            return "Authentication failed. Please try again.";
        }
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
