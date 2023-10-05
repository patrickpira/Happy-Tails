package com.example.happytails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonreg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonreg = findViewById(R.id.btnregister);
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.loginnow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

       buttonreg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progressBar.setVisibility(View.VISIBLE);

               String email,password;
               email = String.valueOf(editTextEmail.getText());
               password = String.valueOf(editTextPassword.getText());

               if(TextUtils.isEmpty(email)){
                   Toast.makeText(register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(TextUtils.isEmpty(password)){
                   Toast.makeText(register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                   return;
               }

               mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressBar.setVisibility(View.GONE);
                               if (task.isSuccessful()) {
                                   Toast.makeText(register.this, "Account Created", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(getApplicationContext(),Login.class);
                                   startActivity(intent);
                                   finish();
                               } else {
                                   // If sign in fails, display a message to the user.
                                   Toast.makeText(register.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();

                               }
                           }
                       });


           }
       });
    }
}