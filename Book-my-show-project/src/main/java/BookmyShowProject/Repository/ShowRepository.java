package BookmyShowProject.Repository;

import BookmyShowProject.Models.Movie;
import BookmyShowProject.Models.Show;
import BookmyShowProject.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Integer> {


    Show findShowByShowDateAndShowTimeAndMovieAndTheater(LocalDate showDate, LocalTime showTime, Movie movie, Theater theater);

    @Query(value = "SELECT * FROM shows WHERE show_date = :date AND show_time = :time and theater_theater_id =:id", nativeQuery = true)
    public Show findShowByDateAndTime(@Param("date") LocalDate date, @Param("time") LocalTime time,@Param("id") int id);

    @Query(value ="select * from shows where show_date = :date" ,nativeQuery = true)
    public List<Show> find_all_show_by_movie_date(@Param("date") LocalDate date);

}
