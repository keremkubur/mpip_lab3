package mk.ukim.finki.mpip.mpip_lab3.api

import mk.ukim.finki.mpip.mpip_lab3.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIOmdb {
    @GET("/")
    fun getMovieByTitle(@Query("t") title: String,
                        @Query("apikey") key: String,
                        @Query("plot") plot: String): Call<Movie>
}