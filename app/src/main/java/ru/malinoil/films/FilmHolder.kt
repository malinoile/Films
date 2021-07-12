package ru.malinoil.films

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.film_card, parent, false)
    ) {
    private var titleTextView: TextView = itemView.findViewById(R.id.card_film_title)
    private var rateTextView: TextView = itemView.findViewById(R.id.card_film_rate)
    private var yearTextView: TextView = itemView.findViewById(R.id.card_film_year)
    private var imageView: ImageView = itemView.findViewById(R.id.card_film_image)

    fun bind(film: Film) {
        fillCard(film)
    }

    private fun fillCard(film: Film) {
        yearTextView.text = film.year.toString()
        film.rate?.apply {
            rateTextView.text = film.rate.toString()
        }
        titleTextView.text = film.name
        film.imageSrc?.apply {
            imageView.setImageURI(Uri.parse(film.imageSrc))
        }
    }
}