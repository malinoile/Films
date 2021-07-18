package ru.malinoil.films.model

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.databinding.HomeComponentBinding

class HomeComponentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding: HomeComponentBinding? = null

    init {
        binding = HomeComponentBinding.bind(itemView)
    }

    fun bind(component: HomeComponent) {
        binding?.run {
            val adapter = FilmsAdapter()
            adapter.setList(component.listFilms)
            typeTitleTextView.text = getTitleTypeName(component.type)
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
