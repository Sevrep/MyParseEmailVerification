package com.sevrep.myparseemailverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    public void signupIsPressed(View view) {
        Toast.makeText(this, "Sign up is pressed.", Toast.LENGTH_SHORT).show();

        try {
            ParseUser user = new ParseUser();
            user.setEmail(edtEmail.getText().toString().trim());
            user.setUsername(edtUsername.getText().toString().trim());
            user.setPassword(edtPassword.getText().toString().trim());
            user.signUpInBackground(e -> {
                if (e == null) {
                    ParseUser.logOut();
                    customAlertDialog("Account created successfully!", "Please verify your email before Login", false);
                } else {
                    ParseUser.logOut();
                    customAlertDialog("Error account creation failed!", "Account could not be created: " + e.getMessage(), true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customAlertDialog(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.cancel();
            if (!error) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog ok = builder.create();
        ok.show();
    }


}