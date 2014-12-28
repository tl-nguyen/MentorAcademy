package com.tlnguyen.cinemaapp.helpers;

import com.parse.ParseFile;
import com.tlnguyen.cinemaapp.models.Cinema;
import com.tlnguyen.cinemaapp.models.Movie;

import java.util.ArrayList;

public class DataSeed {

    public static void seed() {

        // Populate Cinemas
        Cinema cinema1 = new Cinema();
        cinema1.setTitle("Cinema City Paradise");
        cinema1.setAddress("София, ул.\"Черни връх\" 100, Paradise Center");
        cinema1.setWorkingTime("10:00AM - 12:00PM");
        cinema1.setPhotos(new ArrayList<ParseFile>());
        cinema1.setMovies(new ArrayList<Movie>());
        new DownloadCinemaPhotosTask(cinema1).execute("http://www.cinemacity.bg/media/iti_cz/content/bg/imgs/cinema_bullet_paradise.jpg");
        new DownloadCinemaPhotosTask(cinema1).execute("http://programata.bg/img/gallery/place_7037.jpg");
        new DownloadCinemaPhotosTask(cinema1).execute("http://timeart.bg/files/spot2/size3/7387/16363_0.jpg");

        Cinema cinema2 = new Cinema();
        cinema2.setTitle("Арена Deluxe BulgariaMall");
        cinema2.setAddress("Бул. България 69, Търговски център Bulgaria Mall");
        cinema2.setWorkingTime("09:00AM - 12:00PM");
        cinema2.setPhotos(new ArrayList<ParseFile>());
        cinema2.setMovies(new ArrayList<Movie>());
        new DownloadCinemaPhotosTask(cinema2).execute("https://irs0.4sqi.net/img/general/152x152/3424838_B4JU9e6qMazLPtoaosWVYt7NYJ663pq9EYjKfaYT2GI.jpg");
        new DownloadCinemaPhotosTask(cinema2).execute("http://www.dnevnik.bg/shimg/zx600y338_1985976.jpg");
        new DownloadCinemaPhotosTask(cinema2).execute("http://infozone.bg//sites/default/files/logo/17424626284.jpg");

        Cinema cinema3 = new Cinema();
        cinema3.setTitle("Арена Младост");
        cinema3.setAddress("Адрес: ж.к. Младост 4, ул. Бизнес Парк 3");
        cinema3.setWorkingTime("09:00AM - 12:00PM");
        cinema3.setPhotos(new ArrayList<ParseFile>());
        cinema3.setMovies(new ArrayList<Movie>());
        new DownloadCinemaPhotosTask(cinema3).execute("http://91.215.216.77/media/upload/news/219657.jpg");
        new DownloadCinemaPhotosTask(cinema3).execute("http://www.audio-factor.eu/userfiles/image/obekti/%D0%B0%D1%80%D0%B5%D0%BD%D0%B0%20%D0%BC%D0%BB%D0%B0%D0%B4%D0%BE%D1%81%D1%82.jpg");
        new DownloadCinemaPhotosTask(cinema3).execute("http://www.cinefish.bg/news_img/Image/00-NOVINI/2012%20novini/08/The-Expendables-2.jpg");

        // Populate movies
        Movie movie1 = new Movie();
        movie1.setTitle("Into the Woods");
        movie1.setCinemas(new ArrayList<Cinema>());
        new DownloadMoviePictureTask(movie1).execute("http://85.14.28.164/d/images/photos_movies/3286-resized.jpg");

        Movie movie2 = new Movie();
        movie2.setTitle("The Imitation Game");
        movie2.setCinemas(new ArrayList<Cinema>());
        new DownloadMoviePictureTask(movie2).execute("http://www.cinefish.bg/data/movies_images/244/p_244136_thumb.jpg");

        Movie movie3 = new Movie();
        movie3.setTitle("Trash");
        movie3.setCinemas(new ArrayList<Cinema>());
        new DownloadMoviePictureTask(movie3).execute("http://85.14.28.164/d/images/photos_movies/3216-resized.jpg");

        Movie movie4 = new Movie();
        movie4.setTitle("Interstellar");
        movie4.setCinemas(new ArrayList<Cinema>());
        new DownloadMoviePictureTask(movie4).execute("http://85.14.28.164/d/images/photos_movies/3137-resized.jpg");

        Movie movie5 = new Movie();
        movie5.setTitle("The Hobbit: The Battle of the Five Armies");
        movie5.setCinemas(new ArrayList<Cinema>());
        new DownloadMoviePictureTask(movie5).execute("http://85.14.28.164/d/images/photos_movies/3277-resized.jpg");
    }
}
