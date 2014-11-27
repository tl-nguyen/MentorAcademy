package com.tlnguyen.classassignment.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tlnguyen.classassignment.R;
import com.tlnguyen.classassignment.models.User;
import com.tlnguyen.classassignment.persisters.DummyDbManager;

import java.util.ArrayList;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPassword;

    private Button mBtnLogin;

    private TextView mTvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEtPassword = (EditText) findViewById(R.id.etPassword);

        mBtnLogin = (Button) findViewById(R.id.btnLogin);

        mTvSignup = (TextView) findViewById(R.id.tvSignUp);

        mBtnLogin.setOnClickListener(this);
        mTvSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();

                break;
            case R.id.tvSignUp:
                goToRegisterUser();
                break;
        }
    }

    private void login() {
        ArrayList<User> users = DummyDbManager.getInstance().getUsers();
        for (User user: users) {
            if (user.getUsername().equals(this.mEtUsername.getText().toString()) &&
                    user.getPassword().equals(this.mEtPassword.getText().toString())) {

                DummyDbManager.getInstance().setCurrentUser(user);
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }

        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
    }

    private void goToRegisterUser() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
