package ru.malinoil.films.model

import ru.malinoil.films.model.entities.FilmEntity

object FilmsRepository {

    private var listFilms: MutableList<FilmEntity>? = null

    fun getInstance(): FilmsRepository {
        if (listFilms == null) {
            listFilms = emptyList<FilmEntity>().toMutableList()
        }
        return this
    }

    fun updateFilm(film: FilmEntity) {
        getPosition(film)?.let {
            listFilms?.set(it, film)
        }
    }

    private fun getPosition(film: FilmEntity): Int? {
        return listFilms?.indexOf(film)
    }

}