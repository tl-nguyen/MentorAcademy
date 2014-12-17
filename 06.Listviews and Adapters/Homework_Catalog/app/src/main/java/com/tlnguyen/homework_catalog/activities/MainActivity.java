package com.tlnguyen.homework_catalog.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tlnguyen.homework_catalog.R;
import com.tlnguyen.homework_catalog.adapters.BookAdapter;
import com.tlnguyen.homework_catalog.common.Constants;
import com.tlnguyen.homework_catalog.data.BookCatalogDao;
import com.tlnguyen.homework_catalog.data.interfaces.IBookCatalogDao;
import com.tlnguyen.homework_catalog.models.Book;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    private IBookCatalogDao mBookDao;
    private List<Book> mBooks;
    private List<Book> mSelectedBooks;
    private List<Book> mSearchResults;

    private GridView mGvBooks;
    private Menu mMenu;

    private BookAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mBookDao = new BookCatalogDao(this);
        mBooks = mBookDao.getAll();
        mSelectedBooks = new ArrayList<Book>();
        mSearchResults = new ArrayList<Book>();

        mGvBooks = (GridView) findViewById(R.id.gvBooks);
        clearSearch();
        mGvBooks.setOnItemClickListener(this);
        mGvBooks.setOnItemLongClickListener(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mSearchResults = getResults(query);
            mBookAdapter = new BookAdapter(this, mSearchResults);
        } else {
            mBookAdapter = new BookAdapter(this, mBooks);
        }

        mGvBooks.setAdapter(mBookAdapter);
    }

    private List<Book> getResults(String query) {

        ArrayList<Book> queryResults = new ArrayList<Book>();

        for (Book book: mBooks) {
            if (book.getName().toLowerCase().contains(query.toLowerCase())) {
                queryResults.add(book);
            }
        }

        return queryResults;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.mMenu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.add_book:
                goToAddBookScreen();
                break;
            case R.id.delete_books:
                removeSelectedBooks();
                break;
            case R.id.search:
                onSearchRequested();
                break;
            case R.id.clearSearch:
                clearSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearSearch() {
        mBookAdapter = new BookAdapter(this, mBooks);
        mGvBooks.setAdapter(mBookAdapter);
    }

    private void goToAddBookScreen() {
        Intent addBookIntent = new Intent(this, AddBook.class);
        startActivity(addBookIntent);
    }

    private void removeSelectedBooks() {
        mBookDao.remove(mSelectedBooks);

        for(Book book: mSelectedBooks) {
            if (mBooks.contains(book)) {
                mBooks.remove(book);
            }
        }

        mSelectedBooks.clear();
        mBookAdapter.notifyDataSetChanged();

        setDeleteIconVisibility();
    }

    @Override
    protected void onDestroy() {
        mBookDao.releaseDb();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToDetailScreen(position);
    }

    private void goToDetailScreen(int position) {
        Book currentBook = mBooks.get(position);
        Intent bookDetailIntent = new Intent(this, BookDetail.class);
        bookDetailIntent.putExtra(Constants.BOOK, currentBook);
        startActivity(bookDetailIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectBook(view, position);

        return true;
    }

    private void selectBook(View view, int position) {
        Book selectedBook = mBooks.get(position);

        if (!selectedBook.isSelected()) {
            mSelectedBooks.add(selectedBook);
            selectedBook.setSelected(true);
            view.setBackgroundColor(Color.GRAY);
        } else {
            mSelectedBooks.remove(selectedBook);
            selectedBook.setSelected(false);
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        setDeleteIconVisibility();
    }

    private void setDeleteIconVisibility() {
        if (mSelectedBooks.size() > 0) {
            mMenu.findItem(R.id.delete_books).setVisible(true);
        }
        else {
            mMenu.findItem(R.id.delete_books).setVisible(false);
        }
    }
}
