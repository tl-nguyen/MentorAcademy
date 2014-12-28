package com.tlnguyen.cinemaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.helpers.ImageResizer;
import com.tlnguyen.cinemaapp.models.Cinema;

import java.util.List;

public class CinemaAdapter extends BaseAdapter {

    private List<Cinema> mCinemas;
    private Context mContext;

    public CinemaAdapter(Context context, List<Cinema> cinemas) {
        this.mContext = context;
        this.mCinemas = cinemas;
    }

    @Override
    public int getCount() {
        return mCinemas.size();
    }

    @Override
    public Object getItem(int position) {
        return mCinemas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        final CinemaHolder holder;

        if (cell == null) {
            cell = LayoutInflater.from(mContext).inflate(R.layout.cinema_cell, parent, false);

            holder = new CinemaHolder();
            holder.cinemaTitle = (TextView) cell.findViewById(R.id.tvCinemaTitle);
            holder.coverImage = (ImageView) cell.findViewById(R.id.ivCoverImage);

            cell.setTag(holder);
        }
        else {
            holder = (CinemaHolder) cell.getTag();
        }

        Cinema cinema = this.mCinemas.get(position);

        if (cinema != null) {
            holder.cinemaTitle.setText(cinema.getTitle());
            if (cinema.getPhotos() != null) {

                cinema.getPhotos().get(0).getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            holder.coverImage.setImageBitmap(ImageResizer.decodeSampledBitmapFromByteArray(data, 100, 100));
                        } else {
                            // something went wrong
                        }
                    }
                });
            }
        }

        return cell;
    }

    static class CinemaHolder {
        private TextView cinemaTitle;
        private ImageView coverImage;
    }
}
