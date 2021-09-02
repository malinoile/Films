package ru.malinoil.films.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FilmCardBinding
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.ui.fragment.FilmFragment

class FilmsAdapter : RecyclerView.Adapter<FilmHolder>() {
    private var listFilms: List<FilmEntity> = emptyList()
    private var filmClickListener: OnFilmClickListener? = null

    fun setList(list: List<FilmEntity>) {
        listFilms = list;
    }

    fun setOnFilmClickListener(listener: OnFilmClickListener?) {
        filmClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(parent, filmClickListener)
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(listFilms[position])
    }

    override fun getItemCount(): Int {
        return listFilms.size
    }

    interface OnFilmClickListener {
        fun onClick(film: FilmEntity)
    }
}

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
        binding?.filmYearTextView?.text = film.releaseDate.split("-")[0]
        film.rate?.apply {
            binding?.filmRateTextView?.text = film.rate.toString()
        }
        binding?.filmTitleTextView?.text = film.name
        Glide.with(itemView.context).load("${FilmFragment.IMAGE_BASE_URL}${film.poster}")
            .into(binding!!.filmImageView)
    }
}