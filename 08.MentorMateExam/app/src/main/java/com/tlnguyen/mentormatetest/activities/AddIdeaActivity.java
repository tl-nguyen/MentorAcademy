package com.tlnguyen.mentormatetest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tlnguyen.mentormatetest.R;
import com.tlnguyen.mentormatetest.data.DbManager;
import com.tlnguyen.mentormatetest.models.Idea;
import com.tlnguyen.mentormatetest.models.User;

import java.util.List;

public class AddIdeaActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText mEtName;
    private EditText mEtDescription;
    private Button mBtnSave;

    private List<Idea> mIdeas;
    private Idea mIdeaToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idea);

        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        mIdeas = DbManager.getInstance().getIdeas();

        if (extras != null) {
            mIdeaToEdit = extras.getParcelable("EDIT_IDEA");
        }

        mEtName = (EditText) findViewById(R.id.etIdeaName);
        mEtDescription = (EditText) findViewById(R.id.etIdeaDescription);
        mBtnSave = (Button) findViewById(R.id.btnSave);

        if (mIdeaToEdit != null) {
            mEtName.setText(mIdeaToEdit.getName());
            mEtDescription.setText(mIdeaToEdit.getDescription());
        }

        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        switch (id) {
            case R.id.btnSave:
                saveNewIdea();
                break;
        }
    }

    private void saveNewIdea() {
        String name = mEtName.getText().toString();
        String description = mEtDescription.getText().toString();
        User owner = DbManager.getInstance().getCurrentUser();

        Idea idea = null;

        if (mIdeaToEdit != null) {
            for (Idea ideaInList: mIdeas) {
                if (ideaInList.getName().equals(idea.getName())) {
                    idea = ideaInList;
                    idea.setName(name);
                    idea.setDescription(description);
                    break;
                }
            }
        }
        else {
            idea = new Idea(name, description, owner);
            DbManager.getInstance().getIdeas().add(idea);
        }

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
