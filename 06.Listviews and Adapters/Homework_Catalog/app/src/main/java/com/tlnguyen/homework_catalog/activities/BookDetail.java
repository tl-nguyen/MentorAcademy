package com.tlnguyen.homework_catalog.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlnguyen.homework_catalog.R;
import com.tlnguyen.homework_catalog.common.Constants;
import com.tlnguyen.homework_catalog.models.Book;
import com.tlnguyen.homework_catalog.tasks.DownloadImageTask;

public class BookDetail extends Activity {

    private Book mSelectedBook;

    private TextView mTvName;
    private TextView mTvDescription;
    private TextView mTvIsbn;
    private ImageView mIvCoverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        mSelectedBook = bundle.getParcelable(Constants.BOOK);

        mTvName = (TextView) findViewById(R.id.tvBookName);
        mTvDescription = (TextView) findViewById(R.id.tvBookDescription);
        mTvIsbn = (TextView) findViewById(R.id.tvIsbn);
        mIvCoverImage = (ImageView) findViewById(R.id.ivCoverImage);

        mTvName.setText(mSelectedBook.getName());
        mTvDescription.setText(mSelectedBook.getDescription());
        mTvIsbn.setText(mSelectedBook.getIsbn());
        if (mSelectedBook.getImageUrl() != null) {
            new DownloadImageTask(mIvCoverImage).execute(mSelectedBook.getImageUrl());
        }
    }
}
