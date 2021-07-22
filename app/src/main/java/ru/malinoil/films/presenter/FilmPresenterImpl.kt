package ru.malinoil.films.presenter

import ru.malinoil.films.FilmsContract
import ru.malinoil.films.model.Film
import ru.malinoil.films.model.FilmsRepository

class FilmPresenterImpl(film: Film) : FilmsContract.Presenter {
    private var view: FilmsContract.View? = null
    private var filmsRepo: FilmsRepository = FilmsRepository.getInstance()
    private var film: Film? = null

    init {
        this.film = film
    }

    override fun attach(view: FilmsContract.View) {
        this.view = view
    }

    override fun onClickHeart() {
        film?.apply {
            isFavorite = !isFavorite
            filmsRepo.updateFilm(this)
            view?.renderHeart(isFavorite)
        }
    }

    override fun detach() {
        view = null
    }
}