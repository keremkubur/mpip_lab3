package mk.ukim.finki.mpip.mpip_lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.mpip_lab3.adapters.MovieRecyclerAdapter
import mk.ukim.finki.mpip.mpip_lab3.adapters.OnItemClickListener
import mk.ukim.finki.mpip.mpip_lab3.api.APIClient
import mk.ukim.finki.mpip.mpip_lab3.api.APIOmdb
import mk.ukim.finki.mpip.mpip_lab3.database.AppDatabase
import mk.ukim.finki.mpip.mpip_lab3.databinding.FragmentFirstBinding
import mk.ukim.finki.mpip.mpip_lab3.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentFirstBinding? = null

    private lateinit var omdbApiClient: APIOmdb
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var mAdapter: MovieRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        omdbApiClient = APIClient.getOmdbApi()!!

        val movieTitle = view.findViewById<EditText>(R.id.editTitle)

        movieRecyclerView = view.findViewById(R.id.allMovies)

        movieRecyclerView.layoutManager = LinearLayoutManager(activity)

        mAdapter = MovieRecyclerAdapter(mutableListOf(), this)

        movieRecyclerView.setHasFixedSize(true)

        movieRecyclerView.adapter = mAdapter

        movieTitle.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                val movieTitleEt: String = movieTitle.text.toString()
                searchMovieByTitle(movieTitleEt)
                CoroutineScope(Dispatchers.IO).launch {
                }
                true
            } else {
                false
            }
        }
    }

    private fun searchMovieByTitle(movieTitle: String) {
        omdbApiClient.getMovieByTitle(movieTitle, "bfd77cf3", "short").enqueue(object : Callback<Movie>{
            override fun onResponse(call: Call<Movie>?, response: Response<Movie>) {
                displayData(response.body())
                saveMovieInDatabase(response.body())
            }

            override fun onFailure(call: Call<Movie>?, t: Throwable) {
            }
        })
    }

    private fun displayData(movie: Movie) {
        mAdapter.updateData(movie)
    }

    private fun saveMovieInDatabase(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val activity = activity as MainActivity
            val database: AppDatabase = AppDatabase.getInstance(activity)
            database.MovieDAO().insertMovie(movie)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onItemClickListener(movie: Movie) {
        val activity = activity as MainActivity
        activity.setMovieTitle(movie.title)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, null)
    }
}

