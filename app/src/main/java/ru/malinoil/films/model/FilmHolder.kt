package ru.malinoil.films.model

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FilmCardBinding
import ru.malinoil.films.model.entities.FilmEntity

class FilmHolder(parent: ViewGroup, filmClickListener: FilmsAdapter.OnFilmClickListener?) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.film_card, parent, false)
    ) {
    private var binding: FilmCardBinding? = null
    private var film: FilmEntity? = null

    init {
        binding = FilmCardBinding.bind(itemView)

        itemView.setOnClickListener {
            film?.let {
                filmClickListener?.onClick(it)
            }
        }
    }

    fun bind(film: FilmEntity) {
        fillCard(film)
        this.film = film
    }

    private fun fillCard(film: FilmEntity) {
        binding?.filmYearTextView?.text = film.year.toString()
        film.rate?.apply {
            binding?.filmRateTextView?.text = film.rate.toString()
        }
        binding?.filmTitleTextView?.text = film.name
        film.imageSrc?.apply {
            binding?.filmImageView?.setImageURI(Uri.parse(film.imageSrc))
        }
    }

}