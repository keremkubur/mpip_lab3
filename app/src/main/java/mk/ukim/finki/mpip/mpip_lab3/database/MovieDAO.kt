package mk.ukim.finki.mpip.mpip_lab3.database

import androidx.room.*
import mk.ukim.finki.mpip.mpip_lab3.model.Movie

@Dao
abstract class MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovie(movie: Movie)

    @Transaction
    @Query("SELECT * FROM Movie WHERE title = :title")
    abstract fun getMovie(title: String): List<Movie>

}