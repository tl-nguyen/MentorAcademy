package com.tlnguyen.homework_catalog.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.tlnguyen.homework_catalog.R;
import com.tlnguyen.homework_catalog.data.BookCatalogDao;
import com.tlnguyen.homework_catalog.data.interfaces.IBookCatalogDao;
import com.tlnguyen.homework_catalog.models.Book;

public class AddBook extends Activity {

    private IBookCatalogDao mBookDao;

    private EditText mEtName;
    private EditText mEtDescription;
    private EditText mEtIsbn;
    private EditText mEtImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        init();
    }

    private void init() {
        mBookDao = new BookCatalogDao(this);

        mEtName = (EditText) findViewById(R.id.etName);
        mEtDescription = (EditText) findViewById(R.id.etDescription);
        mEtIsbn = (EditText) findViewById(R.id.etIsbn);
        mEtImageUrl = (EditText) findViewById(R.id.etImageUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_book) {
            createBook();
            goToMainScreen();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createBook() {
        String bookName = mEtName.getText().toString();
        String bookDescription = mEtDescription.getText().toString();
        String bookIsbn = mEtIsbn.getText().toString();
        String bookImageUrl = mEtImageUrl.getText().toString();

        Book newBook = new Book(bookName, bookDescription, bookIsbn, bookImageUrl);

        mBookDao.create(newBook);
    }

    private void goToMainScreen() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    protected void onDestroy() {
        mBookDao.releaseDb();
        super.onDestroy();
    }
}
