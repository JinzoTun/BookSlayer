package com.jinzo.mirage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView gitHubRepo = findViewById(R.id.githubIcon);

        gitHubRepo.setOnClickListener(v -> {
                    String githubRepoUrl = "https://github.com/JinzoTun/Mirage";
                    Uri uri = Uri.parse(githubRepoUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                );

        mAuth = FirebaseAuth.getInstance();

        TextView welcomeTextView = findViewById(R.id.textViewWelcome);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Get the email address
            String email = currentUser.getEmail();

            // Extract the part before the '@' sign
            String username = email != null && email.contains("@") ? email.substring(0, email.indexOf('@')) : "";

            // Now set the welcome text with the username
            welcomeTextView.setText("Welcome, " + username);
        }


        Button logoutButton = findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(view -> {
            mAuth.signOut();
            // You can navigate to the login or sign-up activity as needed
            finish();
        });
    }
}
