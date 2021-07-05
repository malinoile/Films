package ru.malinoil.films

object Coping {

    fun copyFilm(film: Film): Film {
        return film.copy(name = "Копия. " + film.name)
    }

}