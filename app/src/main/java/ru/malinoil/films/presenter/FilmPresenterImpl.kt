package ru.malinoil.films.presenter

import ru.malinoil.films.FilmsContract
import ru.malinoil.films.model.FilmsRepository
import ru.malinoil.films.model.entities.FilmEntity

class FilmPresenterImpl(film: FilmEntity) : FilmsContract.Presenter {
    private var view: FilmsContract.View? = null
    private var filmsRepo: FilmsRepository = FilmsRepository.getInstance()
    private var film: FilmEntity? = null

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