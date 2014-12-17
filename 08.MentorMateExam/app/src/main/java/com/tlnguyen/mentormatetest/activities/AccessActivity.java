package com.tlnguyen.mentormatetest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tlnguyen.mentormatetest.R;
import com.tlnguyen.mentormatetest.data.DbManager;
import com.tlnguyen.mentormatetest.models.Role;
import com.tlnguyen.mentormatetest.models.User;

import java.util.ArrayList;

public class AccessActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLoginOrRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        init();
    }

    private void init() {
        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mBtnLoginOrRegister = (Button) findViewById(R.id.btnLoginOrRegister);

        mBtnLoginOrRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnLoginOrRegister:
                giveAccess();
                break;
        }
    }

    private void giveAccess() {
        ArrayList<User> users = DbManager.getInstance().getUsers();
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        boolean userExist = false;

        for (User user: users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                userExist = true;
                login(user);
            }
        }

        if (!userExist) {
            register(username, password);
        }
    }

    private void register(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setId(DbManager.getInstance().getCurrentId());

        if (newUser.getId() == 1) {
            newUser.setRole(Role.ADMIN);
        }
        else {
            newUser.setRole(Role.USER);
        }

        login(newUser);
    }

    private void login(User user) {
        DbManager.getInstance().setCurrentUser(user);
        Intent mainIntent = new Intent(this, MainActivity.class);

        startActivity(mainIntent);
        finish();
    }
}
