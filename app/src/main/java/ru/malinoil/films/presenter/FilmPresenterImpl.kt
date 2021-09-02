package ru.malinoil.films.presenter

import retrofit2.Retrofit
import ru.malinoil.films.model.contracts.FilmsContract
import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.model.repositories.FilmsRepository
import ru.malinoil.films.model.repositories.NotesRepository
import ru.malinoil.films.model.repositories.impls.network.NetworkFilmsRepositoryImpl
import ru.malinoil.films.model.repositories.impls.room.FilmsDatabase
import ru.malinoil.films.model.repositories.impls.room.NoteDTO
import ru.malinoil.films.model.repositories.impls.room.RoomNotesRepositoryImpl

class FilmPresenterImpl(retrofit: Retrofit, database: FilmsDatabase, film: FilmEntity) :
    FilmsContract.Presenter {
    private var view: FilmsContract.View? = null
    private var film: FilmEntity? = null
    private val notesRepository: NotesRepository by lazy { RoomNotesRepositoryImpl(database) }
    private val filmsRepository: FilmsRepository by lazy { NetworkFilmsRepositoryImpl(retrofit) }

    init {
        this.film = film
    }

    override fun attach(view: FilmsContract.View) {
        this.view = view
    }

    override fun getFullInfoAboutFilm(filmId: Int) {
        filmsRepository.getFullInfo(filmId, {
            view?.updateInfoAboutFilm(it)
        }, {
            //todo
        })
    }

    override fun onClickHeart() {
        film?.apply {
            isFavorite = !isFavorite
            view?.renderHeart(isFavorite)
        }
    }

    override fun getNote(filmId: Int) {
        notesRepository.getNote(filmId, { note ->
            note?.let {
                view?.renderNote(it)
            }
        }, {
            //todo
        })
    }

    override fun updateNote(note: NoteDTO) {
        notesRepository.updateNote(note, {
            view?.renderNote(it)
        }, {
            //todo
        })
    }

    override fun deleteNote(filmId: Int) {
        notesRepository.deleteNote(filmId, {
            view?.deleteNote()
        }, {
            //todo
        })
    }

    override fun detach() {
        view = null
    }
}