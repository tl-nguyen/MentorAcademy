package com.tlnguyen.homework_catalog.data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tlnguyen.homework_catalog.models.Book;
import com.tlnguyen.homework_catalog.data.interfaces.IBookCatalogDao;

import java.util.List;

/**
 * Created by TL on 12/5/2014.
 */
public class BookCatalogDao implements IBookCatalogDao {

    private DatabaseHelper dbHelper;
    private RuntimeExceptionDao<Book, Integer> bookDao;

    public BookCatalogDao(Context context) {
        this.dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        this.bookDao = this.dbHelper.getRuntimeDao();
    }

    @Override
    public List<Book> getAll() {
        return bookDao.queryForAll();
    }

    @Override
    public Book getById(int id) {
        return bookDao.queryForId(id);
    }

    @Override
    public void create(Book book) {
        bookDao.create(book);
    }

    @Override
    public void remove(Book book) {
        bookDao.delete(book);
    }

    @Override
    public void remove(List<Book> books) {
        bookDao.delete(books);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void releaseDb() {
        OpenHelperManager.releaseHelper();
    }
}
