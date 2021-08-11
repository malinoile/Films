package ru.malinoil.films.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FilmsCategoryBinding
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

class HomeCategoryHolder(itemView: View, listener: FilmsAdapter.OnFilmClickListener?) :
    RecyclerView.ViewHolder(itemView) {
    private var binding: FilmsCategoryBinding? = null
    private var filmClickListener: FilmsAdapter.OnFilmClickListener? = listener

    init {
        binding = FilmsCategoryBinding.bind(itemView)
    }

    fun bind(componentEntity: CategoryEntity) {
        binding?.run {
            val adapter = FilmsAdapter()
            adapter.setList(componentEntity.listFilms)
            adapter.setOnFilmClickListener(filmClickListener)
            typeTitleTextView.text = getTitleTypeName(componentEntity.type)
            filmRecycler.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            filmRecycler.adapter = adapter
        }
    }

    private fun getTitleTypeName(type: TitleType): String {
        return when (type) {
            TitleType.NEWEST -> itemView.context.getString(R.string.new_title)
            TitleType.COMING_SOON -> itemView.context.getString(R.string.coming_title)
            TitleType.HIGH_RATE -> itemView.context.getString(R.string.high_rate_title)
            TitleType.NOW -> itemView.context.getString(R.string.now_title)
        }
    }
}