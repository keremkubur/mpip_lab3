package mk.ukim.finki.mpip.mpip_lab3.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mk.ukim.finki.mpip.mpip_lab3.database.AppDatabase
import mk.ukim.finki.mpip.mpip_lab3.model.Movie

class DetailsViewModel(application: Application): AndroidViewModel(application){

    private var app: Application = application

    private val database: AppDatabase = AppDatabase.getInstance(application)

    private var movie: Movie? = null

    fun loadMovie(movieTitle: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val tmp = database.MovieDAO().getMovie(movieTitle)

            withContext(Dispatchers.Main){
                movie = tmp[0]
            }
        }
    }

    fun getMovie(): Movie?{
        return movie
    }

}