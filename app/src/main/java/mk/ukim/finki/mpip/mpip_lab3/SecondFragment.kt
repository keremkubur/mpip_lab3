package mk.ukim.finki.mpip.mpip_lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mk.ukim.finki.mpip.mpip_lab3.database.AppDatabase
import mk.ukim.finki.mpip.mpip_lab3.databinding.FragmentSecondBinding
import mk.ukim.finki.mpip.mpip_lab3.model.Movie
import mk.ukim.finki.mpip.mpip_lab3.viewmodels.DetailsViewModel

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var detailsViewModel: DetailsViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        val selectedMovieTitle = activity.getMovieTitle()
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        detailsViewModel.loadMovie(selectedMovieTitle)
        var selectedMovie: Movie? = detailsViewModel.getMovie()
        val database = AppDatabase.getInstance(activity)
        CoroutineScope(Dispatchers.IO).launch {
            selectedMovie = database.MovieDAO().getMovie(selectedMovieTitle)[0]
        }

        CoroutineScope(Dispatchers.Main).launch {

            val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
            val txtReleased = view.findViewById<TextView>(R.id.txtReleaseYear)
            val txtImdb = view.findViewById<TextView>(R.id.txtRating)
            val txtActors = view.findViewById<TextView>(R.id.txtActorsList)
            val txtAwards = view.findViewById<TextView>(R.id.txtAwards)
            val txtPlot = view.findViewById<TextView>(R.id.txtPlot)

            txtTitle.text = selectedMovie?.title
            txtReleased.text = selectedMovie?.released
            txtImdb.text = selectedMovie?.imdbRating
            txtActors.text = selectedMovie?.actors
            txtAwards.text = selectedMovie?.awards
            txtPlot.text = selectedMovie?.plot
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}