package ru.malinoil.films.model.repositories.impls.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteDTO::class], version = 1)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDAO
}