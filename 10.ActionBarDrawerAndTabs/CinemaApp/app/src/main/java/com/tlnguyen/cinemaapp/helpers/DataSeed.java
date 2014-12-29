package com.tlnguyen.cinemaapp.helpers;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.tlnguyen.cinemaapp.models.Cinema;
import com.tlnguyen.cinemaapp.models.Movie;
import com.tlnguyen.cinemaapp.models.MovieCinema;

import java.util.ArrayList;

public class DataSeed {

    public static void seed() {

        // Populate Cinemas
        Cinema cinema1 = new Cinema();
        cinema1.setTitle("Cinema City Paradise");
        cinema1.setAddress("София, ул.\"Черни връх\" 100, Paradise Center");
        cinema1.setWorkingTime("10:00AM - 12:00PM");
        cinema1.setPhotos(new ArrayList<ParseFile>());

        Cinema cinema2 = new Cinema();
        cinema2.setTitle("Арена Deluxe BulgariaMall");
        cinema2.setAddress("Бул. България 69, Търговски център Bulgaria Mall");
        cinema2.setWorkingTime("09:00AM - 12:00PM");
        cinema2.setPhotos(new ArrayList<ParseFile>());

        Cinema cinema3 = new Cinema();
        cinema3.setTitle("Арена Младост");
        cinema3.setAddress("Адрес: ж.к. Младост 4, ул. Бизнес Парк 3");
        cinema3.setWorkingTime("09:00AM - 12:00PM");
        cinema3.setPhotos(new ArrayList<ParseFile>());

        saveCinemas(cinema1, cinema2, cinema3);

        // Populate movies
        Movie movie1 = new Movie();
        movie1.setTitle("Into The Woods");
        movie1.setCast("Актьори: Мерил Стрийп, Емили Блънт, Крис Пайн, Джони Деп, Били Магнусен, Ана Кендрик, Трейси Улман\n" +
                "Режисьор: Роб Маршъл");

        Movie movie2 = new Movie();
        movie2.setTitle("The Imitation Game");
        movie2.setCast("Актьори: Бенедикт Къмбърбач, Кийра Найтли, Матю Гуд, Чарлс Данс, Марк Стронг\n" +
                "Режисьор: Мортен Тилдум");

        Movie movie3 = new Movie();
        movie3.setTitle("Trash");
        movie3.setCast("Актьори: Селтон Мело, Вагнер Моура, Руни Мара, Мартин Шийн, Риксън Тевис, Едуардо Луис\n" +
                "Режисьор: Стивън Долдри");

        Movie movie4 = new Movie();
        movie4.setTitle("Interstellar");
        movie4.setCast("Актьори: Ан Хатауей, Матю Макконъхи, Джесика Частейн, Кейси Афлек, Майкъл Кейн, Уес Бентли, Дейвид Оуелоу \n" +
                "Режисьор: Кристофър Нолан");

        Movie movie5 = new Movie();
        movie5.setTitle("The Hobbit: The Battle of the Five Armies");
        movie5.setCast("Актьори: Мартин Фрийман, Иън Маккелън, Ричард Армитаж, Кейт Бланшет, Люк Евънс, Хюго Уийвинг, Орландо Блум, Бенедикт Къмбърбач, Илайджа Уд, Еванджелин Лили\n" +
                "Режисьор: Питър Джаксън");

        saveMovies(movie1, movie2, movie3, movie4, movie5);

        // Add Movies To Cinemas Relations
        MovieCinema rel1 = new MovieCinema();
        rel1.setAvailableSeats(100);
        rel1.setCinema(cinema1);
        rel1.setMovie(movie1);

        MovieCinema rel2 = new MovieCinema();
        rel2.setAvailableSeats(100);
        rel2.setCinema(cinema1);
        rel2.setMovie(movie3);

        MovieCinema rel3 = new MovieCinema();
        rel3.setAvailableSeats(100);
        rel3.setCinema(cinema1);
        rel3.setMovie(movie4);

        MovieCinema rel4 = new MovieCinema();
        rel4.setAvailableSeats(100);
        rel4.setCinema(cinema2);
        rel4.setMovie(movie2);

        MovieCinema rel5 = new MovieCinema();
        rel5.setAvailableSeats(100);
        rel5.setCinema(cinema2);
        rel5.setMovie(movie3);

        MovieCinema rel6 = new MovieCinema();
        rel6.setAvailableSeats(100);
        rel6.setCinema(cinema2);
        rel6.setMovie(movie5);

        MovieCinema rel7 = new MovieCinema();
        rel7.setAvailableSeats(100);
        rel7.setCinema(cinema3);
        rel7.setMovie(movie1);

        MovieCinema rel8 = new MovieCinema();
        rel8.setAvailableSeats(100);
        rel8.setCinema(cinema3);
        rel8.setMovie(movie4);

        MovieCinema rel9 = new MovieCinema();
        rel9.setAvailableSeats(100);
        rel9.setCinema(cinema3);
        rel9.setMovie(movie5);

        saveRelations(rel1, rel2, rel3, rel4, rel5, rel6, rel7, rel8, rel9);

        // Populate Photos
        new DownloadCinemaPhotosTask(cinema1).execute("http://www.cinemacity.bg/media/iti_cz/content/bg/imgs/cinema_bullet_paradise.jpg");
        new DownloadCinemaPhotosTask(cinema1).execute("http://programata.bg/img/gallery/place_7037.jpg");
        new DownloadCinemaPhotosTask(cinema1).execute("http://timeart.bg/files/spot2/size3/7387/16363_0.jpg");

        new DownloadCinemaPhotosTask(cinema2).execute("https://irs0.4sqi.net/img/general/152x152/3424838_B4JU9e6qMazLPtoaosWVYt7NYJ663pq9EYjKfaYT2GI.jpg");
        new DownloadCinemaPhotosTask(cinema2).execute("http://www.dnevnik.bg/shimg/zx600y338_1985976.jpg");
        new DownloadCinemaPhotosTask(cinema2).execute("http://infozone.bg//sites/default/files/logo/17424626284.jpg");

        new DownloadCinemaPhotosTask(cinema3).execute("http://91.215.216.77/media/upload/news/219657.jpg");
        new DownloadCinemaPhotosTask(cinema3).execute("http://www.audio-factor.eu/userfiles/image/obekti/%D0%B0%D1%80%D0%B5%D0%BD%D0%B0%20%D0%BC%D0%BB%D0%B0%D0%B4%D0%BE%D1%81%D1%82.jpg");
        new DownloadCinemaPhotosTask(cinema3).execute("http://www.cinefish.bg/news_img/Image/00-NOVINI/2012%20novini/08/The-Expendables-2.jpg");

        new DownloadMoviePictureTask(movie1).execute("http://85.14.28.164/d/images/photos_movies/3286-resized.jpg");

        new DownloadMoviePictureTask(movie2).execute("http://www.cinefish.bg/data/movies_images/244/p_244136_thumb.jpg");

        new DownloadMoviePictureTask(movie3).execute("http://85.14.28.164/d/images/photos_movies/3216-resized.jpg");

        new DownloadMoviePictureTask(movie4).execute("http://85.14.28.164/d/images/photos_movies/3137-resized.jpg");

        new DownloadMoviePictureTask(movie5).execute("http://85.14.28.164/d/images/photos_movies/3277-resized.jpg");
    }

    private static void saveRelations(MovieCinema rel1, MovieCinema rel2, MovieCinema rel3, MovieCinema rel4, MovieCinema rel5, MovieCinema rel6, MovieCinema rel7, MovieCinema rel8, MovieCinema rel9) {
        try {
            rel1.save();
            rel2.save();
            rel3.save();
            rel4.save();
            rel5.save();
            rel6.save();
            rel7.save();
            rel8.save();
            rel9.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void saveCinemas(Cinema cinema1, Cinema cinema2, Cinema cinema3) {
        try {
            cinema1.save();
            cinema2.save();
            cinema3.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void saveMovies(Movie movie1, Movie movie2, Movie movie3, Movie movie4, Movie movie5) {
        try {
            movie1.save();
            movie2.save();
            movie3.save();
            movie4.save();
            movie5.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
