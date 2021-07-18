package ru.malinoil.films.model

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter : RecyclerView.Adapter<FilmHolder>() {
    private var listFilms: List<Film> = emptyList()
    private var filmClickListener: OnFilmClickListener? = null

    fun setList(list: List<Film>) {
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
        fun onClick(film: Film)
    }
}