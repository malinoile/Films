package ru.malinoil.films.presenter

import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.model.repositories.NotesRepository
import ru.malinoil.films.model.repositories.impls.FilmsContract
import ru.malinoil.films.model.repositories.impls.room.FilmsDatabase
import ru.malinoil.films.model.repositories.impls.room.NoteDTO
import ru.malinoil.films.model.repositories.impls.room.RoomNotesRepositoryImpl

class FilmPresenterImpl(database: FilmsDatabase, film: FilmEntity) : FilmsContract.Presenter {
    private var view: FilmsContract.View? = null
    private var film: FilmEntity? = null
    private val notesRepository: NotesRepository = RoomNotesRepositoryImpl(database)

    init {
        this.film = film
    }

    override fun attach(view: FilmsContract.View) {
        this.view = view
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