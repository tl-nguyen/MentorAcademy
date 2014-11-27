package com.tlnguyen.classassignment.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tlnguyen.classassignment.R;
import com.tlnguyen.classassignment.common.Role;
import com.tlnguyen.classassignment.models.User;
import com.tlnguyen.classassignment.persisters.DummyDbManager;


public class MainActivity extends Activity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtEmail;

    private EditText mEtUserId;

    private Button mBtnEdit;
    private Button mBtnAdmin;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        currentUser = DummyDbManager.getInstance().getCurrentUser();

        if(currentUser == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        Log.i(TAG, currentUser.getUsername() + " " +
                currentUser.getRole() + " " +
                currentUser.getId());

        mEtUsername = (EditText) findViewById(R.id.etMainUsername);
        mEtPassword = (EditText) findViewById(R.id.etMainPassword);
        mEtEmail = (EditText) findViewById(R.id.etMainEmail);
        mEtUserId = (EditText) findViewById(R.id.etUserId);

        mBtnEdit = (Button) findViewById(R.id.btnEdit);
        mBtnAdmin = (Button) findViewById(R.id.btnAdmin);

        User currentUser = DummyDbManager.getInstance().getCurrentUser();
        mEtUsername.setText(currentUser.getUsername());
        mEtPassword.setText(currentUser.getPassword());
        mEtEmail.setText(currentUser.getEmail());

        mBtnEdit.setOnClickListener(this);
        mBtnAdmin.setOnClickListener(this);

        if (currentUser.getRole() == Role.ADMIN ||
                currentUser.getRole() == Role.MODERATOR) {
            this.mBtnAdmin.setVisibility(View.VISIBLE);
            this.mEtUserId.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        DummyDbManager.getInstance().setCurrentUser(null);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEdit:
                editCurrentUser();
                break;
            case R.id.btnAdmin:
                adminAnUser();
                break;
        }
    }

    private void adminAnUser() {
        int userId = Integer.parseInt(this.mEtUserId.getText().toString().trim());

        Intent adminIntent = new Intent(this, AdminActivity.class);
        adminIntent.putExtra("USER_ID", userId);
        startActivity(adminIntent);
    }

    private void editCurrentUser() {
        String userName = this.mEtUsername.getText().toString();
        String password = this.mEtPassword.getText().toString();

        if (!(userName.equals("") &&
                password.equals(""))) {
            this.currentUser.setUsername(userName);
            this.currentUser.setPassword(password);
            this.currentUser.setEmail(this.mEtEmail.getText().toString());
        }
        else {
            Toast.makeText(this, getString(R.string.validation_error), Toast.LENGTH_SHORT).show();
        }
    }
}
