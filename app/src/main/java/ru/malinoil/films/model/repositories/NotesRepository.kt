package ru.malinoil.films.model.repositories

import ru.malinoil.films.model.repositories.impls.room.NoteDTO

interface NotesRepository {
    fun getNote(filmId: Int, onSuccess: (NoteDTO?) -> Unit, onError: (Throwable) -> Unit)
    fun updateNote(note: NoteDTO, onSuccess: (NoteDTO) -> Unit, onError: (Throwable) -> Unit)
    fun deleteNote(filmId: Int, onSuccess: () -> Unit, onError: (Throwable) -> Unit)
}