package ru.malinoil.films.model.contracts

import ru.malinoil.films.model.entities.FilmEntity
import ru.malinoil.films.model.repositories.impls.room.NoteDTO

interface FilmsContract {
    interface View {
        fun renderHeart(check: Boolean)
        fun renderNote(note: NoteDTO?)
        fun updateInfoAboutFilm(film: FilmEntity)
        fun deleteNote()
    }

    interface Presenter {
        fun attach(view: View)
        fun getFullInfoAboutFilm(filmId: Int)
        fun onClickHeart()
        fun getNote(filmId: Int)
        fun updateNote(note: NoteDTO)
        fun deleteNote(filmId: Int)
        fun detach()
    }
}