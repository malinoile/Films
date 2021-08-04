package ru.malinoil.films.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.model.entities.CategoryEntity

class HomeAdapter : RecyclerView.Adapter<HomeCategoryHolder>() {
    private var list: List<CategoryEntity> = emptyList()
    private var filmClickListener: FilmsAdapter.OnFilmClickListener? = null

    fun setList(categories: List<CategoryEntity>) {
        list = categories
    }

    fun setOnFilmClickListener(filmClickListener: FilmsAdapter.OnFilmClickListener) {
        this.filmClickListener = filmClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryHolder {
        return HomeCategoryHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.films_category, parent, false),
            filmClickListener
        )
    }

    override fun onBindViewHolder(holder: HomeCategoryHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}