package mk.ukim.finki.mpip.mpip_lab3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mk.ukim.finki.mpip.mpip_lab3.R
import mk.ukim.finki.mpip.mpip_lab3.model.Movie

class MovieRecyclerAdapter (var allMovies: MutableList<Movie>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val moviePicture: ImageView= view.findViewById(R.id.mPicture)
        val movieTitle: TextView= view.findViewById(R.id.mTitle)
        val movieYear: TextView = view.findViewById(R.id.mYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = allMovies[position]

        holder.movieTitle.text = currentMovie.title
        holder.movieYear.text = currentMovie.year
        Glide.with(holder.itemView).load(currentMovie.poster).into(holder.moviePicture)

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClickListener(currentMovie)
        }
    }

    override fun getItemCount(): Int {
        return allMovies.size
    }

    fun updateData(movie: Movie) {
        this.allMovies.add(movie)
        this.notifyDataSetChanged()
    }
}

interface OnItemClickListener{
    fun onItemClickListener(movie: Movie)
}