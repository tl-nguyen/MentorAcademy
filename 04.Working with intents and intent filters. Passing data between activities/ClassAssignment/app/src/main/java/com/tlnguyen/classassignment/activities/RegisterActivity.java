package com.tlnguyen.classassignment.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tlnguyen.classassignment.R;
import com.tlnguyen.classassignment.common.Role;
import com.tlnguyen.classassignment.models.User;
import com.tlnguyen.classassignment.persisters.DummyDbManager;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtEmail;

    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        mEtUsername = (EditText) findViewById(R.id.etNewUsername);
        mEtPassword = (EditText) findViewById(R.id.etNewPassword);
        mEtEmail = (EditText) findViewById(R.id.etNewEmail);

        mBtnRegister = (Button) findViewById(R.id.btnEdit);

        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEdit:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        User newUser = new User();

        newUser.setId(DummyDbManager.getInstance().getCurrentId());
        newUser.setUsername(this.mEtUsername.getText().toString());
        newUser.setPassword(this.mEtPassword.getText().toString());
        newUser.setEmail(this.mEtEmail.getText().toString());

        if (newUser.getId() == 1) {
            newUser.setRole(Role.ADMIN);
        }
        else {
            newUser.setRole(Role.USER);
        }

        if (validateUser(newUser)) {
            DummyDbManager.getInstance().getUsers().add(newUser);
            DummyDbManager.getInstance().setCurrentUser(newUser);

            Intent toMainIntent = new Intent(this, MainActivity.class);
            startActivity(toMainIntent);
            finish();
        }
        else {
            Toast.makeText(this, getString(R.string.validation_error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateUser(User newUser) {
        if (newUser.getUsername().equals("")
                || newUser.getPassword().equals("")) {
            return false;
        }

        return true;
    }
}
