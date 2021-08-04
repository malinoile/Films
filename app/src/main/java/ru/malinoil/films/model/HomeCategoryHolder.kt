package ru.malinoil.films.model

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FilmsCategoryBinding
import ru.malinoil.films.model.entities.CategoryEntity

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
