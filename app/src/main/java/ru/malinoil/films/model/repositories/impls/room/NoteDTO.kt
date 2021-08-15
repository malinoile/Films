package ru.malinoil.films.model.repositories.impls.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_notes")
data class NoteDTO(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "film_id") val filmId: Int,
    @ColumnInfo(name = "text") var text: String
)