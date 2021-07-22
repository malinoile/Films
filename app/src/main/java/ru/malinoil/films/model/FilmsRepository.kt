package ru.malinoil.films.model

object FilmsRepository {

    private var listFilms: MutableList<Film>? = null

    fun getInstance(): FilmsRepository {
        if (listFilms == null) {
            listFilms = emptyList<Film>().toMutableList()
            initList()
        }
        return this
    }

    fun getFilmsByType(type: TitleType): MutableList<Film> {
        val list = emptyList<Film>().toMutableList()
        listFilms?.forEach {
            if (it.type == type) {
                list.add(it)
            }
        }
        return list
    }

    fun updateFilm(film: Film) {
        getPosition(film)?.let {
            listFilms?.set(it, film)
        }
    }

    private fun getPosition(film: Film): Int? {
        return listFilms?.indexOf(film)
    }

    private fun initList() {
        listFilms?.run {
            add(Film("Интерстеллар", 2014, 9.2f, 130, null, TitleType.HIGH_RATE))
            add(Film("Форсаж 9", 2021, 7.5f, 100, null, TitleType.NEWEST))
            add(Film("Заклятие 5", 2022, null, null, null, TitleType.COMING_SOON))
            add(Film("Тройной форсаж", 2010, 8.2f, 100, null, TitleType.NOW))
            add(Film("Босс молокосос 2", 2021, 6.8f, 95, null, TitleType.NEWEST))
            add(Film("Тачки 3", 2018, 7.4f, 100, null, TitleType.NOW))
            add(Film("Тачки", 2010, 9.1f, 105, null, TitleType.HIGH_RATE))
        }
    }

}