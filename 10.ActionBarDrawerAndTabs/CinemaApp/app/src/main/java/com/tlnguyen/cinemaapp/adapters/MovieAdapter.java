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
import com.tlnguyen.cinemaapp.models.Movie;

import java.util.List;

public class MovieAdapter extends BaseAdapter {

    private List<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(Context context, List<Movie> cinemas) {
        this.mContext = context;
        this.mMovies = cinemas;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        final MovieHolder holder;

        if (cell == null) {
            cell = LayoutInflater.from(mContext).inflate(R.layout.movie_row, parent, false);

            holder = new MovieHolder();
            holder.movieTitle = (TextView) cell.findViewById(R.id.tvMovieTitle);
            holder.coverImage = (ImageView) cell.findViewById(R.id.ivCoverImage);

            cell.setTag(holder);
        }
        else {
            holder = (MovieHolder) cell.getTag();
        }

        Movie movie = this.mMovies.get(position);

        if (movie != null) {
            holder.movieTitle.setText(movie.getTitle());
            if (movie.getPicture() != null) {

                movie.getPicture().getDataInBackground(new GetDataCallback() {
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

    static class MovieHolder {
        private TextView movieTitle;
        private ImageView coverImage;
    }
}
