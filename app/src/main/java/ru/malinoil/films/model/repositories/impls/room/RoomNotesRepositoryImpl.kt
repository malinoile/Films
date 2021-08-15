package ru.malinoil.films.model.repositories.impls.room

import android.os.Handler
import android.os.Looper
import ru.malinoil.films.model.repositories.NotesRepository

class RoomNotesRepositoryImpl(private val database: FilmsDatabase) : NotesRepository {
    private val handler = Handler(Looper.getMainLooper())

    override fun getNote(
        filmId: Int,
        onSuccess: (NoteDTO?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Thread {
            val note = database.getNotesDao().getFilmNote(filmId)
            handler.post { onSuccess(note) }
        }.start()
    }

    override fun updateNote(
        note: NoteDTO,
        onSuccess: (NoteDTO) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Thread {
            try {
                val dbNote = database.getNotesDao().getFilmNote(note.filmId)
                if (dbNote == null) {
                    database.getNotesDao().saveNote(note)
                } else {
                    database.getNotesDao().updateTextNote(note.text, note.filmId)
                }
                handler.post { onSuccess(note) }
            } catch (thr: Throwable) {
                onError(thr)
            }
        }.start()
    }

    override fun deleteNote(
        filmId: Int,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Thread {
            database.getNotesDao().deleteNote(filmId)
            handler.post { onSuccess() }
        }.start()
    }


}