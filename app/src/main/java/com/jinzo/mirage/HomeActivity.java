package com.jinzo.mirage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jinzo.mirage.BookAdapter.OnItemClickListener;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private BookAdapter bookAdapter;
    private List<Book> books; // Define books at the class level
    private RecyclerView recyclerView; // Define recyclerView at the class level


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        TextView userUid = findViewById(R.id.user_uid);
        TextView userName = findViewById(R.id.user_id);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Get the email address
            String email = currentUser.getEmail();

            // Extract the part before the '@' sign
            String username = email != null && email.contains("@") ? email.substring(0, email.indexOf('@')) : "";

            // Now set the welcome text with the username
            userName.setText("Welcome, " + username);
        }

        //get uid
        String useruid = currentUser.getUid();
        userUid.setText(useruid);

        //get username and avatar

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        ImageView userAvater = findViewById(R.id.user_avatar);

        userAvater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( HomeActivity.this, WelcomeActivity.class));
            }
        });





        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("books");

        // Set up RecyclerView adapter
        bookAdapter = new BookAdapter(new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                openPdf(book.getPdfUrl());
            }
        });
        recyclerView.setAdapter(bookAdapter);

        // Retrieve data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            // Inside HomeActivity.java

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                books = new ArrayList<>();

                for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                    Book book = bookSnapshot.getValue(Book.class);
                    books.add(book);
                }

                // Update RecyclerView adapter with Firebase data
                bookAdapter.setBooks(books);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }

    // Method to open PDF using an Intent
    private void openPdf(String pdfUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle exception if no PDF viewer app is installed
            Toast.makeText(this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
        }
    }
}
