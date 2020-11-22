package com.sevrep.myparseemailverification;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLoginUsername;
    private EditText edtLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
    }

    public void loginIsPressed(View view) {
        String username = edtLoginUsername.getText().toString().trim();
        String password = edtLoginPassword.getText().toString().trim();
        ParseUser.logInInBackground(username, password, (user, e) -> {
            if (user != null) {
                if (user.getBoolean("emailVerified")) {
                    customAlertDialog("Login successful!", "Welcome, " + username + "!", false);
                } else {
                    ParseUser.logOut();
                    customAlertDialog("Login failed!", "Please verify your email first.", true);
                }
            } else {
                ParseUser.logOut();
                customAlertDialog("Login failed!", e.getMessage() + "Please re-try.", true);
            }
        });
    }

    private void customAlertDialog(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.cancel();
            if (!error) {

            }
        });
        AlertDialog ok = builder.create();
        ok.show();
    }
}