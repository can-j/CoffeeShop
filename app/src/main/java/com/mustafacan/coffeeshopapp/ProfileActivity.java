package com.mustafacan.coffeeshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView emailText, sadakatText, coffeeText;
    EditText nameEditText, ageEditText;
    SharedPreferences sharedPreferences;
    String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        emailText = findViewById(R.id.emailTextView);
        sadakatText = findViewById(R.id.sadakatTextView);
        coffeeText = findViewById(R.id.coffeeTextView);
        ageEditText = findViewById(R.id.ageEditText);
        nameEditText = findViewById(R.id.nameEditText);

        emailText.setText(firebaseUser.getEmail().toString());

        currentUserEmail = firebaseUser.getEmail();


        sharedPreferences = this.getSharedPreferences("com.mustafacan.coffeeshopapp", Context.MODE_PRIVATE);

        int storedScore = sharedPreferences.getInt(currentUserEmail+"score", 0);
        int coffeeNo = sharedPreferences.getInt(currentUserEmail+"coffee", 0);
        int storedAge = sharedPreferences.getInt(currentUserEmail+"age",1);
        String storedName = sharedPreferences.getString(currentUserEmail+"name", "No name");

        coffeeText.setText("Kahve Sayısı: " + coffeeNo);
        nameEditText.setText(storedName);
        ageEditText.setText(String.valueOf(storedAge));
        sadakatText.setText("Sadakat Puanı: " + storedScore);


    }

    public void saveClicked(View view){

        if (nameEditText != null && ageEditText != null){

            int age = Integer.parseInt(ageEditText.getText().toString());
            String name = nameEditText.getText().toString();

            sharedPreferences.edit().putInt(currentUserEmail+"age", age).apply();
            sharedPreferences.edit().putString(currentUserEmail+"name", name).apply();
            Toast.makeText(ProfileActivity.this, "User infos saved", Toast.LENGTH_LONG).show();
        }
    }

    public void clearClicked(View view){
        // bedava kahvelerin alınmasından sonra
        // sıfırlama işlemi
        int coffeeNo = 0;
        sharedPreferences.edit().putInt(currentUserEmail + "coffee", coffeeNo).apply();
        coffeeText.setText("Kahve Sayısı: " + coffeeNo);
    }

}