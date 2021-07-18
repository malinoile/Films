package ru.malinoil.films.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R

class HomeAdapter : RecyclerView.Adapter<HomeComponentHolder>() {
    private var list: List<HomeComponent> = emptyList()
    private var filmClickListener: FilmsAdapter.OnFilmClickListener? = null

    fun setList(components: List<HomeComponent>) {
        list = components
    }

    fun setOnFilmClickListener(filmClickListener: FilmsAdapter.OnFilmClickListener) {
        this.filmClickListener = filmClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeComponentHolder {
        return HomeComponentHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_component, parent, false),
            filmClickListener
        )
    }

    override fun onBindViewHolder(holder: HomeComponentHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}