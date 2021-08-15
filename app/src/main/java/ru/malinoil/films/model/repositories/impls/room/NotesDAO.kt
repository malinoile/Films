package ru.malinoil.films.model.repositories.impls.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDAO {
    @Query("SELECT * FROM film_notes WHERE film_id == :filmId")
    fun getFilmNote(filmId: Int): NoteDTO?

    @Insert
    fun saveNote(note: NoteDTO)

    @Query("UPDATE film_notes SET text = :text WHERE film_id == :filmId")
    fun updateTextNote(text: String, filmId: Int)

    @Query("DELETE FROM film_notes WHERE film_id == :filmId")
    fun deleteNote(filmId: Int)
}