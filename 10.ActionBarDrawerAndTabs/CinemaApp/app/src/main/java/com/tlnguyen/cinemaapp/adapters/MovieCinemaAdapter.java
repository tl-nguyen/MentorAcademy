package com.tlnguyen.cinemaapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.models.MovieCinema;

import java.util.List;

public class MovieCinemaAdapter extends BaseAdapter{

    private List<MovieCinema> mMovieCinemas;
    private Context mContext;

    public MovieCinemaAdapter(Context context, List<MovieCinema> cinemas) {
        this.mContext = context;
        this.mMovieCinemas = cinemas;
    }

    @Override
    public int getCount() {
        return mMovieCinemas.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovieCinemas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final MovieCinemaHolder holder;

        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.movie_cinema_row, parent, false);

            holder = new MovieCinemaHolder();
            holder.cinemaTitle = (TextView) row.findViewById(R.id.tvCinemaTitle);
            holder.availableSeats = (TextView) row.findViewById(R.id.tvAvailableSeat);
            holder.view = row;

            row.setTag(holder);
        }
        else {
            holder = (MovieCinemaHolder) row.getTag();
        }

        MovieCinema movieCinema = this.mMovieCinemas.get(position);

        if (movieCinema != null) {
            holder.cinemaTitle.setText(movieCinema.getCinema().getTitle());
            holder.availableSeats.setText(String.valueOf(movieCinema.getAvailableSeats()));
            if (movieCinema.getAvailableSeats() == 0) {
                holder.view.setBackgroundColor(Color.GRAY);
            }
        }

        return row;
    }

    static class MovieCinemaHolder {
        private TextView cinemaTitle;
        private TextView availableSeats;
        private View view;
    }
}
