package ru.malinoil.films.model.repositories.impls

import ru.malinoil.films.model.repositories.impls.room.NoteDTO

interface FilmsContract {
    interface View {
        fun renderHeart(check: Boolean)
        fun renderNote(note: NoteDTO?)
        fun deleteNote()
    }

    interface Presenter {
        fun attach(view: View)
        fun onClickHeart()
        fun getNote(filmId: Int)
        fun updateNote(note: NoteDTO)
        fun deleteNote(filmId: Int)
        fun detach()
    }
}