package com.tlnguyen.classassignment.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        mBtnEdit.setOnClickListener(this);
        mBtnMakeAdmin.setOnClickListener(this);

        mEtUsername.setText(editUser.getUsername());
        mEtPassword.setText(editUser.getPassword());
        mEtEmail.setText(editUser.getEmail());

        if (this.editUser.getRole() != Role.ADMIN) {
            this.mBtnMakeAdmin.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        }
    }

    private void makeAdmin() {
        this.editUser.setRole(Role.ADMIN);
    }

    private void editUser() {
        this.editUser.setUsername(this.mEtUsername.getText().toString());
        this.editUser.setPassword(this.mEtPassword.getText().toString());
        this.editUser.setEmail(this.mEtEmail.getText().toString());
    }
}
