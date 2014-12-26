package com.tlnguyen.cinemaapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tlnguyen.cinemaapp.R;

public class SignupActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtEmail;
    private Button mBtnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {
        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mBtnSignUp = (Button) findViewById(R.id.btnSignUp);

        mBtnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnSignUp:
                signUp();
                break;
        }
    }

    private void signUp() {
        String username = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String email = mEtEmail.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            // The input are empty, show an alert
            showAlert(getString(R.string.dialog_error_title),
                    getString(R.string.signup_invalid_inputs_message));
        }
        else {
            // Create the new user in Parse.com
            createUser(username, password, email);
        }
    }

    private void createUser(String username, String password, String email) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Signed up successfully
                    goToHomeScreen();
                }
                else {
                    showAlert(getString(R.string.dialog_error_title), e.getMessage());
                }
            }
        });
    }

    private void goToHomeScreen() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
