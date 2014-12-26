package com.tlnguyen.cinemaapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.tlnguyen.cinemaapp.R;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mTvSignUp = (TextView) findViewById(R.id.tvSignUp);

        mBtnLogin.setOnClickListener(this);
        mTvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.tvSignUp:
                goToSignUpScreen();
                break;
        }

    }

    private void login() {
        String username = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            // The input are empty, show an alert
            showAlert(getString(R.string.dialog_error_title),
                    getString(R.string.login_invalid_inputs_message));
        }
        else {
            // Log the new user in Parse.com
            ParseUser.logInInBackground(username, password, new LogInCallback() {

                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        // Logged in successfully
                        goToHomeScreen();
                    }
                    else {
                        showAlert(getString(R.string.dialog_error_title),
                                e.getMessage());
                    }
                }
            });
        }
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

    private void goToSignUpScreen() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }
}
