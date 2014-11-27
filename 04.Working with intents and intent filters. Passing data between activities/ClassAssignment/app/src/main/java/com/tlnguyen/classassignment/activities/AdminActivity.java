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

public class AdminActivity extends Activity implements View.OnClickListener{

    private User editUser;

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtEmail;

    private Button mBtnEdit;
    private Button mBtnMakeAdmin;
    private Button mBtnMakeModerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    private void init() {
        Intent obtainedIntent = getIntent();
        int userId = obtainedIntent.getIntExtra("USER_ID", DummyDbManager.getInstance().getCurrentUser().getId());

        for (User user: DummyDbManager.getInstance().getUsers()) {
            if (user.getId() == userId) {
                this.editUser = user;
            }
        }

        mEtUsername = (EditText) findViewById(R.id.etEditUsername);
        mEtPassword = (EditText) findViewById(R.id.etEditPassword);
        mEtEmail = (EditText) findViewById(R.id.etEditEmail);

        mBtnEdit = (Button) findViewById(R.id.btnAdminEdit);
        mBtnMakeAdmin = (Button) findViewById(R.id.btnMakeAdmin);
        mBtnMakeModerator = (Button) findViewById(R.id.btnMakeModerator);

        mBtnEdit.setOnClickListener(this);
        mBtnMakeAdmin.setOnClickListener(this);
        mBtnMakeModerator.setOnClickListener(this);

        mEtUsername.setText(editUser.getUsername());
        mEtPassword.setText(editUser.getPassword());
        mEtEmail.setText(editUser.getEmail());

        if (this.editUser.getRole() != Role.ADMIN &&
            DummyDbManager.getInstance().getCurrentUser().getRole() == Role.ADMIN) {
            this.mBtnMakeAdmin.setVisibility(View.VISIBLE);
        }

        if (this.editUser.getRole() != Role.MODERATOR) {
            this.mBtnMakeModerator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdminEdit:
                editUser();
                break;
            case R.id.btnMakeAdmin:
                makeAdmin();
                break;
            case R.id.btnMakeModerator:
                makeModerator();
                break;
        }
    }

    private void makeModerator() {
        this.editUser.setRole(Role.MODERATOR);
    }

    private void makeAdmin() {
        this.editUser.setRole(Role.ADMIN);
    }

    private void editUser() {
        String userName = this.mEtUsername.getText().toString();
        String password = this.mEtPassword.getText().toString();

        if (!(userName.equals("") &&
                password.equals(""))) {
            this.editUser.setUsername(userName);
            this.editUser.setPassword(password);
            this.editUser.setEmail(this.mEtEmail.getText().toString());
        }
        else {
            Toast.makeText(this, getString(R.string.validation_error), Toast.LENGTH_SHORT).show();
        }
    }
}
