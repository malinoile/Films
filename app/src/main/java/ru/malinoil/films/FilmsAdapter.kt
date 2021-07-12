package ru.malinoil.films

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter : RecyclerView.Adapter<FilmHolder>() {
    private var listFilms: List<Film> = emptyList()

    fun setList(list: List<Film>) {
        listFilms = list;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(parent)
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(listFilms[position])
    }

    override fun getItemCount(): Int {
        return listFilms.size
    }
}