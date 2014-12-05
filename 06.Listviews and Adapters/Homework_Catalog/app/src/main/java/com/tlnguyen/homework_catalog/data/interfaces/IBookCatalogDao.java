package com.tlnguyen.homework_catalog.data.interfaces;

import com.tlnguyen.homework_catalog.models.Book;

import java.util.List;

/**
 * Created by TL on 12/5/2014.
 */
public interface IBookCatalogDao {
    public List<Book> getAll();
    public Book getById(int id);
    public void create(Book book);
    public void remove(Book book);
    public void remove(List<Book> books);
    public void update(Book book);
    public void releaseDb();
}
