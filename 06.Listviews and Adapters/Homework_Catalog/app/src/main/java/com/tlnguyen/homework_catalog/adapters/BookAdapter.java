package com.tlnguyen.homework_catalog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlnguyen.homework_catalog.R;
import com.tlnguyen.homework_catalog.models.Book;
import com.tlnguyen.homework_catalog.tasks.DownloadImageTask;

import java.util.List;

/**
 * Created by TL on 12/5/2014.
 */
public class BookAdapter extends BaseAdapter {

    private List<Book> mBooks;
    private Context mContext;

    public BookAdapter(Context mContext, List<Book> mBooks) {
        this.mContext = mContext;
        this.mBooks = mBooks;
    }

    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public Object getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBooks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        BookHolder holder;

        if (cell == null) {
            cell = LayoutInflater.from(mContext).inflate(R.layout.book_cell, parent, false);

            holder = new BookHolder();
            holder.bookName = (TextView) cell.findViewById(R.id.tvBookName);
            holder.coverImage = (ImageView) cell.findViewById(R.id.ivCoverImage);
            holder.convertView = cell;

            cell.setTag(holder);
        }
        else {
            holder = (BookHolder) cell.getTag();
        }

        Book book = this.mBooks.get(position);

        if (book != null) {
            holder.bookName.setText(book.getName());
            if (book.getImageUrl() != null) {
                new DownloadImageTask(holder.coverImage).execute(book.getImageUrl());
            }
            holder.convertView.setBackgroundColor(book.isSelected() ? Color.GRAY : Color.TRANSPARENT);
        }

        return cell;
    }

    static class BookHolder {
        private TextView bookName;
        private ImageView coverImage;
        private View convertView;
    }
}
